package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.BroadcasterBusiness;
import com.americadigital.libupapi.Dao.Entity.BroadcasterEntity;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Broadcaster.Broadcaster;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Broadcaster.AddBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Request.Broadcaster.StatusBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Request.Broadcaster.UpdateBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.AllBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.BroadcasterListResponse;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.BroadcasterResponse;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.SearchBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BroadcasterBusinessImpl implements BroadcasterBusiness {
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_RADIO + "/";
    private static final Logger LOG = LoggerFactory.getLogger(BroadcasterBusinessImpl.class);


    @Autowired
    private BroadcasterDao broadcasterDao;

    @Autowired
    private BroadcasterPagDao broadcasterPagDao;

    @Override
    public ResponseEntity<HeaderResponse> addBroadcaster(AddBroadcasterRequest request) {
        HeaderResponse response;
        String msg;
        String extension;
        BroadcasterEntity broadcasterEntity = new BroadcasterEntity();
        broadcasterEntity.idBroadcaster = new GenerateUuid().generateUuid();
        broadcasterEntity.isDeleted = false;
        broadcasterEntity.active = true;
        broadcasterEntity.name = request.name;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        //logo de la radiodifusora
        String nameImage = generateNameImg.generateUuidImage();
        DecodeBase64 decodeBase64 = new DecodeBase64();
        BufferedImage bufferedImage = decodeBase64.decodeToImage(request.pathImage);
        if (bufferedImage != null) {
            extension = GetExtensions.getExtensionImage(request.pathImage);
            broadcasterEntity.imagePath = nameImage + extension;
        } else {
            msg = ConstantsTexts.FORMAT_BASE64;
            throw new ConflictException(msg);
        }
        try {
            new SaveImages().saveImages(request.pathImage, IMAGE_FOLDER, nameImage, extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        broadcasterDao.save(broadcasterEntity);
        msg = ConstantsTexts.BROADCASTER_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HeaderResponse> updateBroadcaster(UpdateBroadcasterRequest request) {
        HeaderResponse response;
        String msg;
        String extension;
        String path;
        BroadcasterEntity broadcasterEntity = broadcasterDao.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        path = broadcasterEntity.imagePath;
        broadcasterEntity.name = request.name;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        //logo de la radiodifusora
        String nameImage = generateNameImg.generateUuidImage();
        DecodeBase64 decodeBase64 = new DecodeBase64();
        if (request.pathImage.isPresent() && request.pathImage.get().length() > 0) {
            BufferedImage bufferedImage = decodeBase64.decodeToImage(request.pathImage.get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(request.pathImage.get());
                broadcasterEntity.imagePath = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
            try {
                new SaveImages().saveImages(request.pathImage.get(), IMAGE_FOLDER, nameImage, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        broadcasterDao.save(broadcasterEntity);
        msg = ConstantsTexts.BROADCASTER_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg));
        if (request.pathImage.isPresent() && request.pathImage.get().length() > 0) {
            DeleteImages delImg = new DeleteImages();
            String msgImage = delImg.deleteImage(IMAGE_FOLDER + path);
            LOG.error("Error: " + msgImage);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HeaderResponse> changeStatus(StatusBroadcasterRequest request) {
        String msg;
        HeaderResponse response;
        BroadcasterEntity broadcasterEntity = broadcasterDao.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        broadcasterEntity.active = request.status;
        broadcasterDao.save(broadcasterEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BroadcasterResponse> getBroadcasterById(String idBroadcaster) {
        String msg;
        BroadcasterResponse response;
        BroadcasterEntity broadcasterEntity = broadcasterDao.findById(idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        msg = ConstantsTexts.BROADCASTER_GET;
        response = new BroadcasterResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Broadcaster(broadcasterEntity.idBroadcaster, broadcasterEntity.name, broadcasterEntity.active, broadcasterEntity.isDeleted, broadcasterEntity.imagePath));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BroadcasterListResponse> getBroadcasterListResponse(AllBroadcasterRequest request) {
        String msg;
        BroadcasterListResponse response;
        List<Broadcaster> broadcasterList = new ArrayList<>();
        Page<BroadcasterEntity> allBroadcaster = broadcasterPagDao.findAllBroadcaster(PageRequest.of(request.page, request.maxResults));
        if (allBroadcaster.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        allBroadcaster.forEach(broadcaster -> {
            broadcasterList.add(new Broadcaster(broadcaster.idBroadcaster, broadcaster.name, broadcaster.active, broadcaster.isDeleted, broadcaster.imagePath));
        });
        msg = ConstantsTexts.BROADCASTER_LIST;
        response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), broadcasterList, allBroadcaster.getTotalPages(), allBroadcaster.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BroadcasterListResponse> getBroadcasterListActives() {
        String msg;
        BroadcasterListResponse response;
        List<Broadcaster> broadcasterList = new ArrayList<>();
        List<BroadcasterEntity> allBroadcaster = broadcasterDao.findAllBroadcasterActives();
        if (allBroadcaster.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        allBroadcaster.forEach(broadcaster -> broadcasterList.add(new Broadcaster(broadcaster.idBroadcaster, broadcaster.name, broadcaster.active, broadcaster.isDeleted, broadcaster.imagePath)));
        msg = ConstantsTexts.BROADCASTER_LIST;
        response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), broadcasterList, 0, 0l);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BroadcasterListResponse> searchBroadcaster(SearchBroadcasterRequest request) {
        String msg;
        BroadcasterListResponse response;
        List<Broadcaster> broadcasterList = new ArrayList<>();
        Page<BroadcasterEntity> broadcasterEntities = broadcasterPagDao.searchBroadcaster(request.searchText, PageRequest.of(request.page, Integer.MAX_VALUE));
        if (broadcasterEntities.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        broadcasterEntities.forEach(broadcaster -> {
            broadcasterList.add(new Broadcaster(broadcaster.idBroadcaster, broadcaster.name, broadcaster.active, broadcaster.isDeleted, broadcaster.imagePath));
        });
        msg = ConstantsTexts.BROADCASTER_LIST;
        response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), broadcasterList, broadcasterEntities.getTotalPages(), broadcasterEntities.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
