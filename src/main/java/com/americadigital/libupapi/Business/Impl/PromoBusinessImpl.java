package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.PromoBusiness;
import com.americadigital.libupapi.Dao.Entity.*;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.Category.CategoryDao;
import com.americadigital.libupapi.Dao.Interfaces.Contest.TContestDao;
import com.americadigital.libupapi.Dao.Interfaces.Promo.PromoDao;
import com.americadigital.libupapi.Dao.Interfaces.Promo.PromoPagDao;
import com.americadigital.libupapi.Dao.Interfaces.RCategoryPromo.RCategoryPromoDao;
import com.americadigital.libupapi.Dao.Interfaces.RPromoBranch.RPromoBranchDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.ShopNotifications.ShopNotificationsDao;
import com.americadigital.libupapi.Dao.Interfaces.States.StatesDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.WinnersDirect.WinnersDirectDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Notifications.NotificateLosers;
import com.americadigital.libupapi.Notifications.NotificateWinner;
import com.americadigital.libupapi.Notifications.Notifications;
import com.americadigital.libupapi.Pojos.Promo.PromoGetAll;
import com.americadigital.libupapi.Pojos.Promo.PromosAllCategory;
import com.americadigital.libupapi.Pojos.RCategoryPromo.RCategoryPromoGet;
import com.americadigital.libupapi.Pojos.RPromoBrannch.GetAllPromoBranch;
import com.americadigital.libupapi.Pojos.WinnersDirect.WinnersRandom;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorAwardsRequest;
import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorRequest;
import com.americadigital.libupapi.WsPojos.Request.Promo.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetAllPromosResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromoResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromosAppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service("promoBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class PromoBusinessImpl implements PromoBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(PromoBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_PROMOS + "/";
    private static final String IMAGE_FOLDER_LOST = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_PROMOS_LOST + "/";


    @Autowired
    private PromoDao promoDao;

    @Autowired
    private RCategoryPromoDao rCategoryPromoDao;

    @Autowired
    private RPromoBranchDao rPromoBranchDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private TContestDao tContestDao;

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private PromoPagDao promoPagDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private WinnersDirectDao winnersDirectDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private StatesDao statesDao;

    @Autowired
    private ShopNotificationsDao shopNotificationsDao;

    @Override
    public ResponseEntity<HeaderResponse> addPromo(AddPromoRequest addPromoRequest) {
        String msg = "";
        String extension = "";
        String extensionLost = "";
        HeaderResponse response;
        GenerateUuid generateUuid = new GenerateUuid();
        PromoEntity promoEntity = new PromoEntity();
        promoEntity.setActive(true);
        promoEntity.setCode(addPromoRequest.promotion.code);
        shopDao.findByIdShop(addPromoRequest.promotion.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        try {
            promoEntity.setDueDate(new ConvertToDate().convertDate(addPromoRequest.promotion.dueDate));
            promoEntity.setReleaseDate(new ConvertToDate().convertDate(addPromoRequest.promotion.releaseDate));
        } catch (ParseException e) {
            msg = ConstantsTexts.DATE_INVALID;
            throw new ConflictException(msg);
        }
        if (addPromoRequest.promotion.description.isPresent() && addPromoRequest.promotion.description.get().length() > 0) {
            promoEntity.setDescription(addPromoRequest.promotion.description.get());
        }

        promoEntity.setAwardType(!addPromoRequest.promotion.awardType.isPresent() ? PromoEntity.AwardType.Ninguno : addPromoRequest.promotion.awardType.orElse(PromoEntity.AwardType.Ninguno));
        promoEntity.setWithNotify(!addPromoRequest.promotion.withNotify.isPresent() ? false : addPromoRequest.promotion.withNotify.orElse(false));
        promoEntity.setIdBroadcaster(!addPromoRequest.promotion.idBroadcaster.isPresent() ? null : addPromoRequest.promotion.idBroadcaster.orElse(null));
        promoEntity.setSeconds(!addPromoRequest.promotion.seconds.isPresent() ? null : addPromoRequest.promotion.seconds.orElse(null));
        promoEntity.setAwardConsolation(!addPromoRequest.promotion.consolationAward.isPresent() ? null : addPromoRequest.promotion.consolationAward.orElse(null));
        promoEntity.setIdPromo(generateUuid.generateUuid());
        promoEntity.setIdShop(addPromoRequest.promotion.idShop);
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        DecodeBase64 decodeBase64 = new DecodeBase64();
        BufferedImage bufferedImage = decodeBase64.decodeToImage(addPromoRequest.promotion.image);
        if (bufferedImage != null) {
            extension = GetExtensions.getExtensionImage(addPromoRequest.promotion.image);
            promoEntity.setImage(nameImage + extension);
        } else {
            msg = ConstantsTexts.FORMAT_BASE64;
            throw new ConflictException(msg);
        }
        SaveImages saveImages = new SaveImages();
        try {
            saveImages.saveImages(addPromoRequest.promotion.image, IMAGE_FOLDER, nameImage, extension);
        } catch (Exception e) {
            msg = ConstantsTexts.FILE_EXCEPTION;
            throw new ConflictException(msg);
        }
        promoEntity.setDeleted(false);
        promoEntity.setName(addPromoRequest.promotion.name);
        promoEntity.setPromoType(addPromoRequest.promotion.promoType);
        promoEntity.setUrlPromo(addPromoRequest.promotion.urlPromo);
        promoEntity.setUrlTerms(addPromoRequest.promotion.urlTerms);
        List<RPromoBranchEntity> listBranchRelation = addPromoRelationBranch(addPromoRequest.idBranches, promoEntity);
        promoEntity.setrPromoBranchEntity(listBranchRelation);
        List<RCategoryPromoEntity> categoryPromoEntityList = addPromoCategoryRelation(addPromoRequest.idCategories, promoEntity);
        promoEntity.setrCategoryPromoEntities(categoryPromoEntityList);
        try {
            promoDao.save(promoEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<PromoEntity>> violations = validator.validate(promoEntity);
            for (final ConstraintViolation<PromoEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        msg = ConstantsTexts.PROMO_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private List<RPromoBranchEntity> addPromoRelationBranch(List<String> idBranches, PromoEntity promoEntity) {
        List<RPromoBranchEntity> listBranchRelation = new ArrayList<RPromoBranchEntity>();
        RPromoBranchEntity rPromoBranchEntity;

        GenerateUuid generateUuid;
        for (String branch : idBranches) {
            rPromoBranchEntity = new RPromoBranchEntity();
            generateUuid = new GenerateUuid();
            rPromoBranchEntity.setIdBranch(branch);
            rPromoBranchEntity.setIdPromo(promoEntity.getIdPromo());
            rPromoBranchEntity.setIdPromoBranch(generateUuid.generateUuid());
            rPromoBranchEntity.setPromoEntity(promoEntity);
            listBranchRelation.add(rPromoBranchEntity);
        }
        return listBranchRelation;
    }

    private List<RCategoryPromoEntity> addPromoCategoryRelation(List<String> idCategories, PromoEntity promoEntity) {
        List<RCategoryPromoEntity> categoryPromoEntityList = new ArrayList<>();
        RCategoryPromoEntity rCategoryPromoEntity;
        GenerateUuid generateUuid;
        for (String idCategory : idCategories) {
            generateUuid = new GenerateUuid();
            rCategoryPromoEntity = new RCategoryPromoEntity();
            rCategoryPromoEntity.setIdCategory(idCategory);
            rCategoryPromoEntity.setIdPromo(promoEntity.getIdPromo());
            rCategoryPromoEntity.setIdRcategorypromo(generateUuid.generateUuid());
            rCategoryPromoEntity.setPromoEntity(promoEntity);
            categoryPromoEntityList.add(rCategoryPromoEntity);
        }
        return categoryPromoEntityList;
    }

    @Override
    public ResponseEntity<HeaderResponse> updatePromo(UpdatePromoRequest updatePromoRequest) {
        String msg = "";
        String extension = "";
        String extensionLost = "";
        HeaderResponse response;
        String imageDb;
        PromoEntity promoEntity = promoDao.findByIdPromo(updatePromoRequest.idPromotion).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        imageDb = promoEntity.getImage();
        List<RCategoryPromoEntity> listRelationsPromoCategory = rCategoryPromoDao.findListRelationsPromoCategory(updatePromoRequest.idPromotion);
        List<RPromoBranchEntity> listRelationsPromoBranch = rPromoBranchDao.findListRelationsPromoBranch(updatePromoRequest.idPromotion);
        promoEntity.setActive(true);
        promoEntity.setCode(updatePromoRequest.promotion.code);
        try {
            promoEntity.setDueDate(new ConvertToDate().convertDate(updatePromoRequest.promotion.dueDate));
            promoEntity.setReleaseDate(new ConvertToDate().convertDate(updatePromoRequest.promotion.releaseDate));
        } catch (ParseException e) {
            msg = ConstantsTexts.DATE_INVALID;
            throw new ConflictException(msg);
        }
        if (updatePromoRequest.promotion.description.isPresent() && updatePromoRequest.promotion.description.get().length() > 0) {
            promoEntity.setDescription(updatePromoRequest.promotion.description.get());
        }
        promoEntity.setAwardType(!updatePromoRequest.promotion.awardType.isPresent() ? PromoEntity.AwardType.Ninguno : updatePromoRequest.promotion.awardType.orElse(PromoEntity.AwardType.Ninguno));
        promoEntity.setWithNotify(!updatePromoRequest.promotion.withNotify.isPresent() ? false : updatePromoRequest.promotion.withNotify.orElse(false));
        promoEntity.setIdBroadcaster(!updatePromoRequest.promotion.idBroadcaster.isPresent() ? null : updatePromoRequest.promotion.idBroadcaster.orElse(null));
        promoEntity.setSeconds(!updatePromoRequest.promotion.seconds.isPresent() ? null : updatePromoRequest.promotion.seconds.orElse(null));
        promoEntity.setAwardConsolation(!updatePromoRequest.promotion.consolationAward.isPresent() ? null : updatePromoRequest.promotion.consolationAward.orElse(null));
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        if (updatePromoRequest.promotion.image.isPresent() && updatePromoRequest.promotion.image.get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updatePromoRequest.promotion.image.get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(updatePromoRequest.promotion.image.get());
                promoEntity.setImage(nameImage + extension);
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        promoEntity.setDeleted(false);
        promoEntity.setName(updatePromoRequest.promotion.name);
        promoEntity.setPromoType(updatePromoRequest.promotion.promoType);
        promoEntity.setUrlPromo(updatePromoRequest.promotion.urlPromo);
        promoEntity.setUrlTerms(updatePromoRequest.promotion.urlTerms);
        promoEntity.getrCategoryPromoEntities().clear();
        promoEntity.getrPromoBranchEntity().clear();
        List<RPromoBranchEntity> listBranchRelation = addPromoRelationBranch(updatePromoRequest.idBranches, promoEntity);
        promoEntity.getrPromoBranchEntity().addAll(listBranchRelation);//esto se tiene que hacer cuando se hace update primero limpiar y luego esto
        List<RCategoryPromoEntity> categoryPromoEntityList = addPromoCategoryRelation(updatePromoRequest.idCategories, promoEntity);
        promoEntity.getrCategoryPromoEntities().addAll(categoryPromoEntityList);
        try {
            promoDao.save(promoEntity);
            rPromoBranchDao.deleteAll(listRelationsPromoBranch);
            rCategoryPromoDao.deleteAll(listRelationsPromoCategory);
            if (updatePromoRequest.promotion.image.isPresent() && updatePromoRequest.promotion.image.get().length() > 0) {
                SaveImages saveImages = new SaveImages();
                DeleteImages delImg = new DeleteImages();
                try {
                    saveImages.saveImages(updatePromoRequest.promotion.image.get(), IMAGE_FOLDER, nameImage, extension);
                    String msgImage = delImg.deleteImage(IMAGE_FOLDER + imageDb);
                    LOG.error("Error: " + msgImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<PromoEntity>> violations = validator.validate(promoEntity);
            for (final ConstraintViolation<PromoEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        msg = ConstantsTexts.PROMO_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changeStatusPromo(ChangeStatusPromo changeStatusPromo) {
        String msg;
        HeaderResponse response;
        PromoEntity promoEntity = promoDao.findByIdPromo(changeStatusPromo.idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        promoEntity.setActive(changeStatusPromo.status);
        promoDao.save(promoEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deletePromo(String idPromo) {
        String msg;
        HeaderResponse response;
        PromoEntity promoEntity = promoDao.findByIdPromo(idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        if(promoEntity.getPromoType().equals(PromoEntity.PromoType.Descuento) || promoEntity.getPromoType().equals(PromoEntity.PromoType.Promoción)){
            List<TContestEntity> contestByIdPromo = tContestDao.findContestByIdPromo(idPromo);
            if(contestByIdPromo.size() > 0){
                throw new ConflictException(ConstantsTexts.PROMO_DELETE_CONTEST);
            }
            List<PromoEntity> awardsWithPromoOrDiscount = promoDao.findAwardsWithPromoOrDiscount(idPromo);
            if (awardsWithPromoOrDiscount.size() > 0){
                throw new ConflictException(ConstantsTexts.PROMO_DELETE);
            }
        }
        promoEntity.setDeleted(true);
        promoDao.save(promoEntity);
        msg = ConstantsTexts.DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllPromosResponse> filterPromosForIdShop(AllPromosShopRequest allPromosShopRequest) {
        shopDao.findByIdShop(allPromosShopRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<PromoEntity> allPromos = promoPagDao.findAllPromoByIdShop(allPromosShopRequest.idShop, allPromosShopRequest.type.toString(), PageRequest.of(allPromosShopRequest.page, allPromosShopRequest.maxResults));
        return extractDataPromos(allPromos);
    }

    @Override
    public ResponseEntity<GetAllPromosResponse> getAwardsDirectosForIdShop(AllAwardsRequest allAwardsRequest) {
        shopDao.findByIdShop(allAwardsRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<PromoEntity> allPromos = promoPagDao.findAllAwardsDirectosByIdShop(allAwardsRequest.idShop, PageRequest.of(allAwardsRequest.page, allAwardsRequest.maxResults));
        return extractDataPromos(allPromos);
    }

    @Override
    public ResponseEntity<GetAllPromosResponse> getAwardsAudioForIdShop(AllAwardsRequest allAwardsRequest) {
        shopDao.findByIdShop(allAwardsRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<PromoEntity> allPromos = promoPagDao.findAllAwardsAudioByIdShop(allAwardsRequest.idShop, PageRequest.of(allAwardsRequest.page, allAwardsRequest.maxResults));
        return extractDataPromos(allPromos);
    }


    @Override
    public ResponseEntity<GetPromosAppResponse> getPromosActivesByIdCategory(GetPromoByCategoryRequest request) {
        String msg;
        GetPromosAppResponse response;
        categoryDao.findByIdCategory(request.idCategory).orElseThrow(() -> new ConflictException(ConstantsTexts.CATEGORY_INVALID));
        List<RCategoryPromoEntity> listPromosByCategory = rCategoryPromoDao.findListPromosByCategory(request.idCategory);
        if (listPromosByCategory.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<String> idsPromos = new ArrayList<>();
        listPromosByCategory.forEach(promos -> {
            idsPromos.add(promos.idPromo);
        });
        List<PromoEntity> listPromosById = promoDao.findListPromosByIdAndType(idsPromos, request.promoType.toString());
        if (listPromosById.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.PROMO_LIST;
        List<PromosAllCategory> promosAllCategories = new ArrayList<>();
        int inState = 0;
        for (PromoEntity promo : listPromosById) {
            inState = 0;
            List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
            promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));

            for (RPromoBranchEntity rPromoBranchEntity : promo.rPromoBranchEntity) {
                //aqui checo que pertenezca a los estados
                if (rPromoBranchEntity.branchEntity.idState.equals(request.idState)) {
                    inState++;
                }
            }
            if (inState > 0) {
                promosAllCategories.add(new PromosAllCategory(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                        ConstantsTexts.fecha.format(promo.getReleaseDate()), ConstantsTexts.fecha.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                        promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promoBranchList, promo.shopEntity.urlCommerce, promo.shopEntity.image));
            }
        }
        response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), promosAllCategories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Async
    @Override
    public ResponseEntity<GetPromosAppResponse> searchPromosOrDiscounts(SearchPromosRequest request) {
        statesDao.findById(request.idState).orElseThrow(() -> new ConflictException(ConstantsTexts.STATE_INVALID));
        if (request.activeLocation) {
            return searchPromosByDistance(request);
        } else {
            return searchPromosActives(request);
        }
    }

    private ResponseEntity<GetPromosAppResponse> searchPromosByDistance(SearchPromosRequest request) {
        String msg;
        GetPromosAppResponse response;
        List<PromosAllCategory> promosAllCategories = new ArrayList<>();
        List<PromoEntity> listPromosActivesWithType = promoDao.searchListPromosActivesWithType(request.promoType.toString(), request.searchText);
        if (listPromosActivesWithType.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<BranchEntity> branchNearestByCoordinates = branchDao.findBranchNearestByCoordinates(request.latitude, request.longitude, request.range);
        List<String> shopsList = new ArrayList<>();
        for (BranchEntity branchEntity : branchNearestByCoordinates) {
            boolean existence = shopsList.stream().anyMatch(o -> o.equals(branchEntity.idShop));
            if (!existence) {
                shopsList.add(branchEntity.idShop);
            }
        }
        int inState = 0;
        for (String idShop : shopsList) {
            for (PromoEntity promo : listPromosActivesWithType) {
                inState = 0;
                if (idShop.equals(promo.idShop)) {
                    List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
                    promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));
                    boolean existence = promosAllCategories.stream().anyMatch(o -> o.idPromo.equals(promo.idPromo));
                    if (!existence) {
                        for (RPromoBranchEntity rPromoBranchEntity : promo.rPromoBranchEntity) {
                            //aqui checo que pertenezca a los estados
                            if (rPromoBranchEntity.branchEntity.idState.equals(request.idState)) {
                                //aqui checo que las sucursales que pertenecen a la promo se encuentren dentro de las que estuvieron cerca en el endpoint
                                for (BranchEntity branchEntity : branchNearestByCoordinates) {
                                    if (rPromoBranchEntity.idBranch.equals(branchEntity.idBranch)) {
                                        inState++;
                                    }
                                }
                            }
                        }
                        if (inState > 0) {
                            promosAllCategories.add(new PromosAllCategory(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                                    ConstantsTexts.fecha.format(promo.getReleaseDate()), ConstantsTexts.fecha.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                                    promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promoBranchList, promo.shopEntity.urlCommerce, promo.shopEntity.image));
                        }
                    }
                }
            }
        }
        msg = ConstantsTexts.PROMO_LIST;
        response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), promosAllCategories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<GetPromosAppResponse> searchPromosActives(SearchPromosRequest request) {
        String msg;
        int inState = 0;
        GetPromosAppResponse response;
        List<PromosAllCategory> promosAllCategories = new ArrayList<>();
        List<PromoEntity> listPromosActivesWithType = promoDao.searchListPromosActivesWithType(request.promoType.toString(), request.searchText);
        for (PromoEntity promo : listPromosActivesWithType) {
            inState = 0;
            List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
            promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));
            for (RPromoBranchEntity rPromoBranchEntity : promo.rPromoBranchEntity) {
                if (rPromoBranchEntity.branchEntity.idState.equals(request.idState)) {
                    inState++;
                }
            }
            if (inState > 0) {
                promosAllCategories.add(new PromosAllCategory(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                        ConstantsTexts.fecha.format(promo.getReleaseDate()), ConstantsTexts.fecha.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                        promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promoBranchList, promo.shopEntity.urlCommerce, promo.shopEntity.image));
            }
        }
        msg = ConstantsTexts.PROMO_LIST;
        response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), promosAllCategories);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> notificatorAwardWinnerDirect(NotificatorAwardsRequest request) {
        String msg;
        HeaderResponse response;
        PromoEntity promoEntity = promoDao.findById(request.idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PREMIO_INVALID));
        List<UserEntity> usersByCoordinates;
        List<UserEntity> userActives;
        ShopEntity shopEntity = shopDao.findByIdShop(request.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        if (request.allUsers) {
            userActives = userEntityDao.getUserActives();
            if (userActives.size() > 0) {
                WinnersRandom userRandomWinner = getUserRandomWinner(userActives);
                if (request.sendNotification) {
                    sendNotificatorWinner(userRandomWinner.deviceToken, shopEntity);
                    notificateLosers(userActives, userRandomWinner, promoEntity, shopEntity);
                }
                saveUserWinner(request, userRandomWinner.userId);
            }
        } else {
            usersByCoordinates = userEntityDao.findUsersByCoordinates(request.latitude, request.longitude, request.rangeKilometers);
            if (usersByCoordinates.size() > 0) {
                WinnersRandom userRandomWinner = getUserRandomWinner(usersByCoordinates);
                if (request.sendNotification) {
                    sendNotificatorWinner(userRandomWinner.deviceToken, shopEntity);
                    notificateLosers(usersByCoordinates, userRandomWinner, promoEntity, shopEntity);
                }
                saveUserWinner(request, userRandomWinner.userId);
            }
        }
        if (request.sendNotification) {
            contabilizarNotifications(request.typeNotify, request.idShop);
        }
        msg = ConstantsTexts.SEND_NOTIFY_WINNER;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void notificateLosers(List<UserEntity> participantes, WinnersRandom winnersRandom, PromoEntity promoEntity, ShopEntity shopEntity) {
        PromoEntity awardConsolation = promoDao.findById(promoEntity.awardConsolation).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        List<String> tokensDevices = new ArrayList<>();
        tokensDevices.add("");
        for (UserEntity p : participantes) {
            UserEntity userEntity = userEntityDao.findById(p.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
            if (!userEntity.idUser.equals(winnersRandom.userId)) {
                String token = userEntity.tokenAndroid == null ? "" : userEntity.tokenAndroid;
                tokensDevices.add(token);
            }
        }
        try {
            NotificateLosers.sendNotificationsLosers(tokensDevices, ConstantsTexts.TICKTEAR_LOSERS, promoEntity.getAwardConsolation(), shopEntity.name, shopEntity.idShop, awardConsolation.getImage(), shopEntity.image);
        } catch (IOException e) {
            String msg;
            msg = ConstantsTexts.NOTIFICATE_ERROR + StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.info(msg);
            throw new ConflictException(msg);
        }
    }

    private WinnersRandom getUserRandomWinner(List<UserEntity> userEntities) {
        List<WinnersRandom> tokensDevices = new ArrayList<>();
        for (UserEntity users : userEntities) {
            String tokenAndroid = users.tokenAndroid == null ? "" : users.tokenAndroid;
            tokensDevices.add(new WinnersRandom(tokenAndroid, users.idUser));
        }
        Random random = new Random();
        int randomitem = random.nextInt(tokensDevices.size());
        WinnersRandom winnersRandom = tokensDevices.get(randomitem);
        return winnersRandom;
    }

    private WinnerDirectEntity saveUserWinner(NotificatorAwardsRequest request, String idUserWinner) {
        WinnerDirectEntity winnerDirectEntity = new WinnerDirectEntity();
        winnerDirectEntity.idWinner = new GenerateUuid().generateUuid();
        winnerDirectEntity.statusWinner = WinnersEntity.StatusWinner.Ganado;
        winnerDirectEntity.idShop = request.idShop;
        winnerDirectEntity.winningDate = new Date();
        winnerDirectEntity.idPromo = request.idPromo;
        winnerDirectEntity.idUser = idUserWinner;
        WinnerDirectEntity save = winnersDirectDao.save(winnerDirectEntity);
        return save;
    }

    private String sendNotificatorWinner(String deviceWinner, ShopEntity shopEntity) {
        String msg;
        String msgResponse;
        try {
            msgResponse = NotificateWinner.sendPushNotification(deviceWinner, ConstantsTexts.WINNER_DIRECT_TYPE, shopEntity.name);
        } catch (IOException e) {
            msg = ConstantsTexts.NOTIFICATE_ERROR + StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        return msgResponse;
    }

    private String sendMultipleNotifications(List<UserEntity> userEntities, NotificatorRequest notificatorRequest, PromoEntity.PromoType promoType, ShopEntity shopEntity) {
        String msg;
        String msgResponse;
        String msgFirebase = "";
        String typeNotify = "";
        try {
            List<String> tokensDevices = new ArrayList<>();
            for (UserEntity users : userEntities) {
                String tokenAndroid = users.tokenAndroid == null ? "" : users.tokenAndroid;
                tokensDevices.add(tokenAndroid);
            }
            if (promoType.toString().equals("Promoción")) {
                msgFirebase = ConstantsTexts.PROMOS_MSG;
                typeNotify = ConstantsTexts.PROMO_TYPE;
            } else if (promoType.toString().equals("Descuento")) {
                msgFirebase = ConstantsTexts.DISCOUNTS_MSG;
                typeNotify = ConstantsTexts.DISCOUNT_TYPE;
            }
            msgResponse = Notifications.sendPushNotification(tokensDevices, notificatorRequest.idShop, notificatorRequest.idPromo, notificatorRequest.namePromo, notificatorRequest.pathImage, msgFirebase, typeNotify, shopEntity.name, shopEntity.urlCommerce);

        } catch (IOException e) {
            msg = ConstantsTexts.NOTIFICATE_ERROR + StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        return msgResponse;
    }

    private void contabilizarNotifications(ShopNotificationsEntity.TypeNotify typeNotify, String idShop) {
        ShopNotificationsEntity shopNotificationsEntity = new ShopNotificationsEntity();
        shopNotificationsEntity.createdTime = new Date();
        shopNotificationsEntity.typeNotify = typeNotify;
        shopNotificationsEntity.uidShop = idShop;
        shopNotificationsDao.save(shopNotificationsEntity);
    }

    @Override
    public ResponseEntity<HeaderResponse> sendNotificatorUsers(NotificatorRequest notificatorRequest) {
        String msg;
        HeaderResponse response;
        PromoEntity promoEntity = promoDao.findById(notificatorRequest.idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PREMIO_INVALID));
        List<UserEntity> usersByCoordinates;
        List<UserEntity> userActives;
        ShopEntity shopEntity = shopDao.findByIdShop(notificatorRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        if (notificatorRequest.allUsers) {
            userActives = userEntityDao.getUserActives();
            if (userActives.size() > 0) {
                sendMultipleNotifications(userActives, notificatorRequest, promoEntity.getPromoType(), shopEntity);
            }
        } else {
            usersByCoordinates = userEntityDao.findUsersByCoordinates(notificatorRequest.latitude, notificatorRequest.longitude, notificatorRequest.rangeKilometers);
            if (usersByCoordinates.size() > 0) {
                sendMultipleNotifications(usersByCoordinates, notificatorRequest, promoEntity.getPromoType(), shopEntity);
            }
        }
        msg = ConstantsTexts.SEND_NOTIFY;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        //CONTABILIZAR NOTIFICACIONES ENVIADAS POR COMERCIO
        contabilizarNotifications(notificatorRequest.typeNotify, notificatorRequest.idShop);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllPromosResponse> getPromotionsOrDiscountsByIdShop(AllPromosRequest allPromosRequest) {
        shopDao.findByIdShop(allPromosRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<PromoEntity> allPromos = promoPagDao.findAllPromosOrDiscountsByIdShop(allPromosRequest.idShop, allPromosRequest.promoType.toString(), PageRequest.of(allPromosRequest.page, allPromosRequest.maxResults));
        return extractDataPromos(allPromos);
    }

    @Override
    public ResponseEntity<GetPromoResponse> getPromoOrDiscountById(String idPromo) {
        String msg;
        GetPromoResponse response;
        PromoEntity promo = promoDao.findById(idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));

        List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
        List<RCategoryPromoGet> rCategoryPromos = new ArrayList<>();
        promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));
        promo.getrCategoryPromoEntities().forEach(cat -> rCategoryPromos.add(new RCategoryPromoGet(cat.getIdRcategorypromo(), cat.getCategoryEntity().idCategory, cat.getCategoryEntity().name)));
        Optional<PromoEntity> promoActive = promoDao.checkActivePromo(promo.idPromo);
        msg = ConstantsTexts.PROMO_GET;
        response = new GetPromoResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new PromoGetAll(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                ConstantsTexts.fechaPromo.format(promo.getReleaseDate()), ConstantsTexts.fechaPromo.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promo.getAwardConsolation(), promoBranchList, rCategoryPromos, promoActive.isPresent(),
                promo.getAwardType(), promo.isWithNotify(), promo.getIdBroadcaster(), promo.getSeconds()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllPromosResponse> getAllPromosOrdDiscounts(String idShop) {
        String msg;
        GetAllPromosResponse response;
        shopDao.findByIdShop(idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        List<PromoEntity> allPromosOrDiscountsByIdShop = promoDao.findAllPromosOrDiscountsByIdShop(idShop);
        if (allPromosOrDiscountsByIdShop.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.PROMO_LIST;
        List<PromoGetAll> promoGetAll = new ArrayList<>();
        for (PromoEntity promo : allPromosOrDiscountsByIdShop) {
            List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
            List<RCategoryPromoGet> rCategoryPromos = new ArrayList<>();
            promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));
            promo.getrCategoryPromoEntities().forEach(cat -> rCategoryPromos.add(new RCategoryPromoGet(cat.getIdRcategorypromo(), cat.getCategoryEntity().idCategory, cat.getCategoryEntity().name)));
            Optional<PromoEntity> promoActive = promoDao.checkActivePromo(promo.idPromo);
            promoGetAll.add(new PromoGetAll(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                    ConstantsTexts.fechaPromo.format(promo.getReleaseDate()), ConstantsTexts.fechaPromo.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                    promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promo.getAwardConsolation(), promoBranchList, rCategoryPromos, promoActive.isPresent(),
                    promo.getAwardType(), promo.isWithNotify(), promo.getIdBroadcaster(), promo.getSeconds()));
        }
        response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), promoGetAll, 0, 0l);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private ResponseEntity<GetAllPromosResponse> extractDataPromos(Page<PromoEntity> allPromos) {
        String msg;
        GetAllPromosResponse response;
        if (allPromos.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        msg = ConstantsTexts.PROMO_LIST;
        List<PromoGetAll> promoGetAll = new ArrayList<>();
        for (PromoEntity promo : allPromos) {
            List<GetAllPromoBranch> promoBranchList = new ArrayList<>();
            List<RCategoryPromoGet> rCategoryPromos = new ArrayList<>();
            promo.getrPromoBranchEntity().forEach(b -> promoBranchList.add(new GetAllPromoBranch(b.idPromoBranch, b.branchEntity.idBranch, b.branchEntity.address)));
            promo.getrCategoryPromoEntities().forEach(cat -> rCategoryPromos.add(new RCategoryPromoGet(cat.getIdRcategorypromo(), cat.getCategoryEntity().idCategory, cat.getCategoryEntity().name)));
            Optional<PromoEntity> promoActive = promoDao.checkActivePromo(promo.idPromo);
            promoGetAll.add(new PromoGetAll(promo.getIdPromo(), promo.getName(), promo.getDescription(), promo.getUrlTerms(), promo.getUrlPromo(), promo.getImage(),
                    ConstantsTexts.fechaPromo.format(promo.getReleaseDate()), ConstantsTexts.fechaPromo.format(promo.getDueDate()), promo.getPromoType(), promo.getCode(),
                    promo.isActive(), promo.isDeleted(), promo.getIdShop(), promo.getShopEntity().name, promo.getAwardConsolation(), promoBranchList, rCategoryPromos, promoActive.isPresent(),
                    promo.getAwardType(), promo.isWithNotify(), promo.getIdBroadcaster(), promo.getSeconds()));
        }
        response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), promoGetAll, allPromos.getTotalPages(), allPromos.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
