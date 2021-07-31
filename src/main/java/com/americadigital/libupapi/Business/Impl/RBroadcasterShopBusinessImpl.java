package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.RBroadcasterShopBusiness;
import com.americadigital.libupapi.Dao.Entity.BroadcasterEntity;
import com.americadigital.libupapi.Dao.Entity.RBroadcasterShopEntity;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.RShopBoadcaster.RelationShopBroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.RBroadcasterShop.GetRelationsBroadcasterShop;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.GenerateUuid;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.Promo.AddOrRemoveRelationRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.AddRelationBroadcasterShopRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.GetRelationsMatchRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.RBroadcasterShop.GetAllRBroadcasterShopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("rBroadcasterShopBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class RBroadcasterShopBusinessImpl implements RBroadcasterShopBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(RBroadcasterShopBusinessImpl.class);

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BroadcasterDao broadcasterDao;

    @Autowired
    private RelationShopBroadcasterDao relationShopBroadcasterDao;

    @Override
    public ResponseEntity<HeaderResponse> addBroadcasterToShop(AddRelationBroadcasterShopRequest request) {
        String msg;
        HeaderResponse response;
        BroadcasterEntity broadcasterEntity = broadcasterDao.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        shopDao.findById(request.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Optional<RBroadcasterShopEntity> relation = relationShopBroadcasterDao.existRelationShopBroadcaster(request.idShop, request.idBroadcaster);
        if (relation.isPresent()) {
            msg = ConstantsTexts.BROADCASTER_SHOP_IS_ADD + broadcasterEntity.name;
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        RBroadcasterShopEntity rBroadcasterShopEntity = new RBroadcasterShopEntity();
        rBroadcasterShopEntity.active = false;
        rBroadcasterShopEntity.idBroadcaster = request.idBroadcaster;
        rBroadcasterShopEntity.idRelation = new GenerateUuid().generateUuid();
        rBroadcasterShopEntity.idShop = request.idShop;
        relationShopBroadcasterDao.save(rBroadcasterShopEntity);
        msg = ConstantsTexts.BROADCASTER_SHOP_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteRelationBroadcasterShop(String idRelationBroadcaster) {
        String msg;
        HeaderResponse response;
        relationShopBroadcasterDao.findById(idRelationBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_SHOP_INVALID));
        relationShopBroadcasterDao.deleteById(idRelationBroadcaster);
        msg = ConstantsTexts.BROADCASTER_SHOP_DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllRBroadcasterShopResponse> getAllRelationsBroadcasterShop(String idShop) {
        shopDao.findById(idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<RBroadcasterShopEntity> byIdShop = relationShopBroadcasterDao.findByIdShop(idShop);
        return extractDataRelations(byIdShop);
    }

    private ResponseEntity<GetAllRBroadcasterShopResponse> extractDataRelations(List<RBroadcasterShopEntity> listRelations) {
        String msg;
        GetAllRBroadcasterShopResponse response;
        List<GetRelationsBroadcasterShop> broadcasterShopList = new ArrayList<>();
        if (listRelations.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        listRelations.forEach(rBroadcasterShopEntity -> {
            broadcasterShopList.add(new GetRelationsBroadcasterShop(rBroadcasterShopEntity.idRelation, rBroadcasterShopEntity.idShop, rBroadcasterShopEntity.shopEntity.name, rBroadcasterShopEntity.idBroadcaster, rBroadcasterShopEntity.broadcasterEntity.name, rBroadcasterShopEntity.active));
        });
        msg = ConstantsTexts.BROADCASTER_SHOP_LIST;
        response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), broadcasterShopList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsBroadcasterShopsActives(String idShop) {
        shopDao.findById(idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<RBroadcasterShopEntity> byIdShop = relationShopBroadcasterDao.findRelationsActivesByIdShop(idShop);
        return extractDataRelations(byIdShop);
    }

    @Override
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsByIdBroadcaster(GetRelationsMatchRequest request) {
        broadcasterDao.findById(request.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        List<RBroadcasterShopEntity> byIdShop = relationShopBroadcasterDao.findRelationsByIdBroadcaster(request.idBroadcaster, request.active);
        return extractDataRelations(byIdShop);
    }

    @Override
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsActivesByIdBroadcaster(String idBroadcaster) {
        broadcasterDao.findById(idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        List<RBroadcasterShopEntity> byIdShop = relationShopBroadcasterDao.findRelationsActivesByIdBroadcaster(idBroadcaster);
        return extractDataRelations(byIdShop);
    }

    @Override
    public ResponseEntity<HeaderResponse> addOrRemoveRelation(AddOrRemoveRelationRequest request) {
        String msg;
        HeaderResponse response;
        RBroadcasterShopEntity rBroadcasterShopEntity = relationShopBroadcasterDao.findById(request.idRelation).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_SHOP_INVALID));
        rBroadcasterShopEntity.active = request.active;
        relationShopBroadcasterDao.save(rBroadcasterShopEntity);
        msg = ConstantsTexts.BROADCASTER_RELATION;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
