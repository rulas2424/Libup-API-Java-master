package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.ChannelsBusiness;
import com.americadigital.libupapi.Dao.Entity.ChannelsEntity;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Channels.ChannelsDao;
import com.americadigital.libupapi.Dao.Interfaces.Channels.ChannelsPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Channels.ChannelsGet;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Channel.AddChannelRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.ChannelStatusRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.GetChannelsRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.UpdateChannelRequest;
import com.americadigital.libupapi.WsPojos.Response.Channels.ChannelListResponse;
import com.americadigital.libupapi.WsPojos.Response.Channels.GetChannelResponse;
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
public class ChannelsBusinessImpl implements ChannelsBusiness {
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_CHANNELS + "/";
    private static final Logger LOG = LoggerFactory.getLogger(ChannelsBusinessImpl.class);

    @Autowired
    private ChannelsDao channelsDao;

    @Autowired
    BroadcasterDao broadcasterDa;

    @Autowired
    ChannelsPagDao channelsPagDao;

    @Override
    public ResponseEntity<HeaderResponse> createChannel(AddChannelRequest request) {
        String msg;
        HeaderResponse response;
        String extension;
        broadcasterDa.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        ChannelsEntity channelsEntity = new ChannelsEntity();
        channelsEntity.active = true;
        channelsEntity.isDeleted = false;
        channelsEntity.idChannel = new GenerateUuid().generateUuid();
        if (request.description.isPresent() && request.description.get().length() > 0) {
            channelsEntity.description = request.description.get();
        }
        channelsEntity.idBroadcaster = request.idBroadcaster;
        channelsEntity.tittle = request.tittle;
        if (request.url.isPresent() && request.url.get().length() > 0) {
            channelsEntity.url = request.url.get();
        }
        //logo del canal
        String nameImage = new GenerateNameImg().generateUuidImage();
        DecodeBase64 decodeBase64 = new DecodeBase64();
        BufferedImage bufferedImage = decodeBase64.decodeToImage(request.image);
        if (bufferedImage != null) {
            extension = GetExtensions.getExtensionImage(request.image);
            channelsEntity.image = nameImage + extension;
        } else {
            msg = ConstantsTexts.FORMAT_BASE64;
            throw new ConflictException(msg);
        }
        try {
            new SaveImages().saveImages(request.image, IMAGE_FOLDER, nameImage, extension);
        } catch (IOException e) {
            e.printStackTrace();
        }
        channelsDao.save(channelsEntity);
        msg = ConstantsTexts.CHANNELS_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> updateChannel(UpdateChannelRequest updateChannelRequest) {
        String msg;
        HeaderResponse response;
        String extension;
        String path;
        ChannelsEntity channelsEntity = channelsDao.findById(updateChannelRequest.idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        path = channelsEntity.image;
        if (updateChannelRequest.description.isPresent() && updateChannelRequest.description.get().length() > 0) {
            channelsEntity.description = updateChannelRequest.description.get();
        }
        channelsEntity.tittle = updateChannelRequest.tittle;
        if (updateChannelRequest.url.isPresent() && updateChannelRequest.url.get().length() > 0) {
            channelsEntity.url = updateChannelRequest.url.get();
        }
        //logo del canal
        String nameImage = new GenerateNameImg().generateUuidImage();
        if (updateChannelRequest.image.isPresent() && updateChannelRequest.image.get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updateChannelRequest.image.get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(updateChannelRequest.image.get());
                channelsEntity.image = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
            try {
                new SaveImages().saveImages(updateChannelRequest.image.get(), IMAGE_FOLDER, nameImage, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        channelsDao.save(channelsEntity);
        msg = ConstantsTexts.CHANNELS_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        if (updateChannelRequest.image.isPresent() && updateChannelRequest.image.get().length() > 0) {
            DeleteImages delImg = new DeleteImages();
            String msgImage = delImg.deleteImage(IMAGE_FOLDER + path);
            LOG.error("Error: " + msgImage);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changeStatus(ChannelStatusRequest channelStatusRequest) {
        String msg;
        HeaderResponse response;
        ChannelsEntity channelsEntity = channelsDao.findById(channelStatusRequest.idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        channelsEntity.active = channelStatusRequest.status;
        msg = ConstantsTexts.ACTIVE;
        channelsDao.save(channelsEntity);
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetChannelResponse> getChannelById(String idChannel) {
        String msg;
        GetChannelResponse response;
        ChannelsEntity channelsEntity = channelsDao.findById(idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        msg = ConstantsTexts.CHANNEL_GET;
        response = new GetChannelResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ChannelsGet(channelsEntity.idChannel, channelsEntity.tittle, channelsEntity.description, channelsEntity.url, channelsEntity.active, channelsEntity.isDeleted, channelsEntity.image, channelsEntity.idBroadcaster, channelsEntity.broadcasterEntity.name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ChannelListResponse> getChannelsForPanel(GetChannelsRequest request) {
        broadcasterDa.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        Page<ChannelsEntity> allChannelsByIdBroadcaster = channelsPagDao.findAllChannelsByIdBroadcaster(request.idBroadcaster, PageRequest.of(request.page, request.maxResults));
        return extractDataChannels(allChannelsByIdBroadcaster);
    }

    @Override
    public ResponseEntity<ChannelListResponse> getChannelsActives() {
        String msg;
        ChannelListResponse response;
        List<ChannelsGet> channelsGets = new ArrayList<>();
        List<ChannelsEntity> allChannelsActives = channelsDao.findAllChannelsActives();
        if (allChannelsActives.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        allChannelsActives.forEach(m -> channelsGets.add(new ChannelsGet(m.idChannel, m.tittle, m.description, m.url, m.active, m.isDeleted, m.image, m.idBroadcaster, m.broadcasterEntity.name)));
        msg = ConstantsTexts.CHANNEL_LIST;

        response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), channelsGets);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<ChannelListResponse> extractDataChannels(Page<ChannelsEntity> channelsEntities) {
        String msg;
        ChannelListResponse response;
        List<ChannelsGet> channelsGets = new ArrayList<>();
        if (channelsEntities.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        channelsEntities.forEach(m -> channelsGets.add(new ChannelsGet(m.idChannel, m.tittle, m.description, m.url, m.active, m.isDeleted, m.image, m.idBroadcaster, m.broadcasterEntity.name)));
        msg = ConstantsTexts.CHANNEL_LIST;

        response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), channelsGets, channelsEntities.getTotalPages(), channelsEntities.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteChannel(String idChannel) {
        String msg;
        HeaderResponse response;
        ChannelsEntity channelsEntity = channelsDao.findById(idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        channelsEntity.isDeleted = true;
        channelsDao.save(channelsEntity);
        msg = ConstantsTexts.DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
