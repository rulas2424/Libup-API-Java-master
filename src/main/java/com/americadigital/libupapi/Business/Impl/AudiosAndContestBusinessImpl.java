package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.AudiosAdnContestBusiness;
import com.americadigital.libupapi.Dao.Entity.*;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Contest.TContestDao;
import com.americadigital.libupapi.Dao.Interfaces.Contest.TContestPagDao;
import com.americadigital.libupapi.Dao.Interfaces.ContestDetail.ContestDetailDao;
import com.americadigital.libupapi.Dao.Interfaces.Promo.PromoDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.ShopNotifications.ShopNotificationsDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.Winners.WinnersEntityDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Notifications.NotificateLosers;
import com.americadigital.libupapi.Notifications.NotificateWinner;
import com.americadigital.libupapi.Notifications.NotificatesTicktear;
import com.americadigital.libupapi.Pojos.Contest.AddContestNotAudio;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Contest.AllContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ChangeStatusContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ContestShopRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.TerminateContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Notifications.NotificationAcrRequest;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestGetResponse;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestListResponse;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestResponse;
import com.americadigital.libupapi.WsPojos.Response.Contest.PromoContestResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.File;
import java.io.IOException;
import java.util.*;


@Service("audiosBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class AudiosAndContestBusinessImpl implements AudiosAdnContestBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(AudiosAndContestBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_AUDIO + "/";

    @Value("${acr.accessKey}")
    private String accessKey;

    @Value("${acr.accessSecret}")
    String accessSecret;

    public String urlAudios = "https://api.acrcloud.com/v1/audios";

    @Autowired
    private TContestDao tContestDao;

    @Autowired
    private TContestPagDao tContestPagDao;

    @Autowired
    private PromoDao promoDao;

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private NotificatesTicktear notificatesTicktear;

    @Autowired
    private NotificateWinner notificateWinner;

    @Autowired
    private ContestDetailDao contestDetailDao;

    @Autowired
    private WinnersEntityDao winnersEntityDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BroadcasterDao broadcasterDao;

    @Autowired
    private ShopNotificationsDao shopNotificationsDao;

    @Override
    public ResponseEntity<HeaderResponse> uploadAudioAndAddContest(String audioTitle, String dataType, String bucketName, MultipartFile file, String idAdmin, String idPromo, String idShop, String idBroadcaster) {
        HeaderResponse response;
        String msg = "";
        TContestEntity tContestEntity;
        shopDao.findByIdShop(idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        broadcasterDao.findById(idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        promoDao.findByIdPromo(idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        userAdminDao.findByIdAdmin(idAdmin).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        SaveAudios saveAudios = new SaveAudios();
        String pathName = saveAudios.fileUpload(file, IMAGE_FOLDER);
        String result = upload(new GenerateUuid().generateUuid(), audioTitle, dataType,
                bucketName, accessKey, accessSecret, file);
        if (result == null) {
            throw new ConflictException(ConstantsTexts.AUDIO_ERROR);
        }
        JSONObject jsonObject = new JSONObject(result);
        LOG.info(result);
        if (jsonObject.length() == 4) {
            throw new ConflictException(jsonObject.get("message").toString());
        }
        tContestEntity = new TContestEntity();
        String idContest = new GenerateUuid().generateUuid();
        tContestEntity.setIdContest(idContest);
        tContestEntity.setIdAdmin(idAdmin);
        tContestEntity.setIdPromo(idPromo);
        tContestEntity.setIdBroadcaster(idBroadcaster);
        tContestEntity.setReleaseDate(new Date());
        tContestEntity.setStatusContest(TContestEntity.StatusContest.Creado);
        tContestEntity.setIdAcr(jsonObject.get("acr_id").toString());
        tContestEntity.setAudioTitle(jsonObject.get("title").toString());
        tContestEntity.setAudio(pathName);
        tContestEntity.setBucketName(bucketName);
        tContestEntity.setDataType(dataType);
        tContestEntity.setContainAudio(true);
        tContestEntity.setIdShop(idShop);
        tContestEntity.setAudioId(jsonObject.get("audio_id").toString());
        try {
            tContestDao.save(tContestEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<TContestEntity>> violations = validator.validate(tContestEntity);
            for (final ConstraintViolation<TContestEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }

        msg = ConstantsTexts.AUDIO_CONTEST_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HeaderResponse> createContestNotAudio(AddContestNotAudio request) {
        HeaderResponse response;
        String msg = "";
        TContestEntity tContestEntity;
        shopDao.findByIdShop(request.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        promoDao.findByIdPromo(request.idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        userAdminDao.findByIdAdmin(request.idAdmin).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        tContestEntity = new TContestEntity();
        tContestEntity.setIdContest(new GenerateUuid().generateUuid());
        tContestEntity.setIdAdmin(request.idAdmin);
        tContestEntity.setIdPromo(request.idPromo);
        tContestEntity.setReleaseDate(new Date());
        tContestEntity.setStatusContest(TContestEntity.StatusContest.Creado);
        tContestEntity.setIdAcr(null);
        tContestEntity.setAudioTitle(null);
        tContestEntity.setAudio(null);
        tContestEntity.setBucketName(null);
        tContestEntity.setDataType("sinAudio");
        tContestEntity.setContainAudio(false);
        tContestEntity.setAudioId(null);
        tContestEntity.setIdShop(request.idShop);
        try {
            tContestDao.save(tContestEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<TContestEntity>> violations = validator.validate(tContestEntity);
            for (final ConstraintViolation<TContestEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }

        msg = ConstantsTexts.AUDIO_CONTEST_ADD;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CREATED, HttpStatus.CREATED.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<HeaderResponse> updateStatusContest(ChangeStatusContestRequest changeStatusContestRequest) {
        String msg;
        HeaderResponse response;
        TContestEntity tContestEntity = tContestDao.findByIdContest(changeStatusContestRequest.idContest).orElseThrow(() -> new ConflictException(ConstantsTexts.CONTEST_INVALID));
        tContestEntity.setStatusContest(changeStatusContestRequest.statusContest);
        tContestDao.save(tContestEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> updateAudioAndContest(String audioTitle, Optional<MultipartFile> file, String idContest) {
        HeaderResponse response;
        String msg = "";
        String acrIdOriginal = "";
        String audioOriginal = "";
        TContestEntity tContestEntity;
        tContestEntity = tContestDao.findByIdContest(idContest).orElseThrow(() -> new ConflictException(ConstantsTexts.CONTEST_INVALID));
        acrIdOriginal = tContestEntity.getIdAcr();
        audioOriginal = tContestEntity.getAudio();
        if (file.isPresent()) {
            SaveAudios saveAudios = new SaveAudios();
            String pathName = saveAudios.fileUpload(file.get(), IMAGE_FOLDER); //agregar la ruta correcta
            String result = upload(tContestEntity.getAudioId(), audioTitle, tContestEntity.getDataType(),
                    tContestEntity.getBucketName(), accessKey, accessSecret, file.get());
            if (result == null) {
                throw new ConflictException(ConstantsTexts.AUDIO_ERROR);
            }
            JSONObject jsonObject = new JSONObject(result);
            LOG.info(result);
            if (jsonObject.length() == 4) {
                throw new ConflictException(jsonObject.get("message").toString());
            }
            deleteAudio(accessKey, accessSecret, acrIdOriginal);
            DeleteImages delImg = new DeleteImages();
            delImg.deleteImage(IMAGE_FOLDER + audioOriginal);
            tContestEntity.setIdAcr(jsonObject.get("acr_id").toString());
            tContestEntity.setAudioTitle(jsonObject.get("title").toString());
            tContestEntity.setAudio(pathName);
        }
        tContestEntity.setBucketName(tContestEntity.getBucketName());
        try {
            tContestDao.save(tContestEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<TContestEntity>> violations = validator.validate(tContestEntity);
            for (final ConstraintViolation<TContestEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }

        msg = ConstantsTexts.AUDIO_CONTEST_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String deleteAudio(String accessKey,
                               String accessSecret, String acrId) {
        String result = null;
        String reqUrl = urlAudios + "/" + acrId;
        String htttMethod = "DELETE";
        String httpAction = "/v1/audios/" + acrId;
        String signatureVersion = "1";
        String timestamp = this.getUTCTimeSeconds();


        String sigStr = htttMethod + "\n" + httpAction + "\n" + accessKey
                + "\n" + signatureVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(),
                accessSecret.getBytes());
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("access-key", accessKey);
        headerParams.put("signature-version", signatureVersion);
        headerParams.put("signature", signature);
        headerParams.put("timestamp", timestamp);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(reqUrl);
        if (headerParams != null) {
            for (String key : headerParams.keySet()) {
                String value = headerParams.get(key);
                httpDelete.addHeader(key, value);
            }
        }
        try {
            HttpResponse response = httpclient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
            if (result == null) {
                throw new ConflictException(ConstantsTexts.AUDIO_DELETE);
            }
            JSONObject jsonObject = new JSONObject(result);
            LOG.info(result);
            if (jsonObject.length() == 4) {
                throw new ConflictException(jsonObject.get("message").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                throw new ConflictException("Error: " + StringFormatUtilities.exceptionMsgBuilder(e));
            }
        }
        return result;
    }

    @Override
    public ResponseEntity<ContestGetResponse> getContestByIdAcr(String idAcr) {
        String msg;
        ContestGetResponse response;
        ContestResponse addData;
        TContestEntity tContestEntity = tContestDao.findContestByAcr(idAcr).orElseThrow(() -> new ConflictException(ConstantsTexts.ACR_INVALID));
        if (tContestEntity.getStatusContest().equals(TContestEntity.StatusContest.Terminado)) {
            msg = ConstantsTexts.CONTEST_END;
            response = new ContestGetResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            LOG.info(msg);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        addData = new ContestResponse(tContestEntity.getIdContest(), tContestEntity.getIdAdmin(), tContestEntity.getUserAdminEntity().name + " " + tContestEntity.getUserAdminEntity().lastName,
                new PromoContestResponse(tContestEntity.getPromoEntity().getIdPromo(), tContestEntity.getPromoEntity().getName(), tContestEntity.getPromoEntity().getDescription(), tContestEntity.getPromoEntity().getUrlTerms(),
                        tContestEntity.getPromoEntity().getUrlPromo(), tContestEntity.getPromoEntity().getImage(), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getReleaseDate()), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getDueDate()),
                        tContestEntity.getPromoEntity().getPromoType(), tContestEntity.getPromoEntity().getCode(), tContestEntity.getPromoEntity().isActive(), tContestEntity.getPromoEntity().getIdShop(), tContestEntity.getPromoEntity().getSeconds(), tContestEntity.getPromoEntity().getAwardType()),
                tContestEntity.getStatusContest(), tContestEntity.getIdAcr(), tContestEntity.getAudioTitle(), tContestEntity.getAudio(), ConstantsTexts.fecha.format(tContestEntity.getReleaseDate()), tContestEntity.getDataType(), tContestEntity.getBucketName(), tContestEntity.getIdBroadcaster(), tContestEntity.getIdBroadcaster() == null ? null : tContestEntity.getBroadcasterEntity().name, tContestEntity.isContainAudio());
        msg = ConstantsTexts.CONTEST_GET;
        response = new ContestGetResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), addData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContestListResponse> getListContestByIdBroadcaster(AllContestRequest allContestRequest) {
        String msg;
        ContestListResponse response;
        List<ContestResponse> contestResponseList = new ArrayList<>();
        Page<TContestEntity> contestByStatusLIst = tContestPagDao.getContestListByIdBroadcaster(allContestRequest.idBroadcaster, allContestRequest.containAudio, PageRequest.of(allContestRequest.page, allContestRequest.maxResults));
        if (contestByStatusLIst.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        contestByStatusLIst.forEach(tContestEntity -> {
            contestResponseList.add(new ContestResponse(tContestEntity.getIdContest(), tContestEntity.getIdAdmin(), tContestEntity.getUserAdminEntity().name + " " + tContestEntity.getUserAdminEntity().lastName,
                    new PromoContestResponse(tContestEntity.getPromoEntity().getIdPromo(), tContestEntity.getPromoEntity().getName(), tContestEntity.getPromoEntity().getDescription(), tContestEntity.getPromoEntity().getUrlTerms(),
                            tContestEntity.getPromoEntity().getUrlPromo(), tContestEntity.getPromoEntity().getImage(), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getReleaseDate()), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getDueDate()),
                            tContestEntity.getPromoEntity().getPromoType(), tContestEntity.getPromoEntity().getCode(), tContestEntity.getPromoEntity().isActive(), tContestEntity.getPromoEntity().getIdShop(), tContestEntity.getPromoEntity().getSeconds(), tContestEntity.getPromoEntity().getAwardType()),
                    tContestEntity.getStatusContest(), tContestEntity.getIdAcr(), tContestEntity.getAudioTitle(), tContestEntity.getAudio(), ConstantsTexts.fecha.format(tContestEntity.getReleaseDate()), tContestEntity.getDataType(), tContestEntity.getBucketName(), tContestEntity.getIdBroadcaster(), tContestEntity.getIdBroadcaster() == null ? null : tContestEntity.getBroadcasterEntity().name, tContestEntity.isContainAudio()));
        });
        msg = ConstantsTexts.CONTEST_LIST;
        response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), contestResponseList, contestByStatusLIst.getTotalPages(), contestByStatusLIst.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ContestListResponse> getListContestByIdShop(ContestShopRequest contestShopRequest) {
        String msg;
        ContestListResponse response;
        List<ContestResponse> contestResponseList = new ArrayList<>();
        Page<TContestEntity> contestByStatusLIst = tContestPagDao.getContestListForIdShop(contestShopRequest.idShop, PageRequest.of(contestShopRequest.page, contestShopRequest.maxResults));
        if (contestByStatusLIst.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        contestByStatusLIst.forEach(tContestEntity -> {
            contestResponseList.add(new ContestResponse(tContestEntity.getIdContest(), tContestEntity.getIdAdmin(), tContestEntity.getUserAdminEntity().name + " " + tContestEntity.getUserAdminEntity().lastName,
                    new PromoContestResponse(tContestEntity.getPromoEntity().getIdPromo(), tContestEntity.getPromoEntity().getName(), tContestEntity.getPromoEntity().getDescription(), tContestEntity.getPromoEntity().getUrlTerms(),
                            tContestEntity.getPromoEntity().getUrlPromo(), tContestEntity.getPromoEntity().getImage(), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getReleaseDate()), ConstantsTexts.fecha.format(tContestEntity.getPromoEntity().getDueDate()),
                            tContestEntity.getPromoEntity().getPromoType(), tContestEntity.getPromoEntity().getCode(), tContestEntity.getPromoEntity().isActive(), tContestEntity.getPromoEntity().getIdShop(), tContestEntity.getPromoEntity().getSeconds(), tContestEntity.getPromoEntity().getAwardType()),
                    tContestEntity.getStatusContest(), tContestEntity.getIdAcr(), tContestEntity.getAudioTitle(), tContestEntity.getAudio(), ConstantsTexts.fecha.format(tContestEntity.getReleaseDate()), tContestEntity.getDataType(), tContestEntity.getBucketName(), tContestEntity.getIdBroadcaster(), tContestEntity.getIdBroadcaster() == null ? null : tContestEntity.getBroadcasterEntity().name, tContestEntity.isContainAudio()));
        });
        msg = ConstantsTexts.CONTEST_LIST;
        response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), contestResponseList, contestByStatusLIst.getTotalPages(), contestByStatusLIst.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> sendNotifications(NotificationAcrRequest notificationAcrRequest) {
        HeaderResponse response;
        String msg;
        TContestEntity tContestEntity = tContestDao.findByIdContest(notificationAcrRequest.idContest).orElseThrow(() -> new ConflictException(ConstantsTexts.CONTEST_INVALID));
        List<String> tokensDevices = new ArrayList<>();
        tokensDevices.add("");
        if (notificationAcrRequest.allUsers) {
            List<UserEntity> userActives = userEntityDao.getUserActives();
            for (UserEntity users : userActives) {
                String tokenAndroid = users.tokenAndroid == null ? "" : users.tokenAndroid;
                tokensDevices.add(tokenAndroid);
            }
        } else {
            List<UserEntity> usersByCoordinates = userEntityDao.findUsersByCoordinates(notificationAcrRequest.latitude, notificationAcrRequest.longitude, notificationAcrRequest.rangeKilometers);
            for (UserEntity users : usersByCoordinates) {
                String tokenAndroid = users.tokenAndroid == null ? "" : users.tokenAndroid;
                tokensDevices.add(tokenAndroid);
            }
        }
        try {
            NotificatesTicktear.sendPushNotification(tokensDevices, notificationAcrRequest.acrId, notificationAcrRequest.withAudio ? ConstantsTexts.TICKTEAR_TYPE : ConstantsTexts.TICKTEAR_NOT_AUDIO, notificationAcrRequest.secondsDuration, notificationAcrRequest.idContest, tContestEntity.promoEntity.shopEntity.name);
            //si no contiene audio se debe contabilizar la notificacion
            if (!notificationAcrRequest.withAudio) {
                contabilizarNotifications(notificationAcrRequest.typeNotify, tContestEntity.idShop);
            }
        } catch (IOException e) {
            msg = ConstantsTexts.NOTIFICATE_ERROR + StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.info(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        tContestEntity.setStatusContest(TContestEntity.StatusContest.EnCurso);
        tContestDao.save(tContestEntity);
        msg = ConstantsTexts.SEND_NOTIFY;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ContestDetailEntity getUserRandomWinner(List<ContestDetailEntity> concursantes) {
        Random random = new Random();
        int randomitem = random.nextInt(concursantes.size());
        return concursantes.get(randomitem);
    }

    @Override
    public ResponseEntity<HeaderResponse> terminateContest(TerminateContestRequest terminateContestRequest) {
        String msg = "";
        HeaderResponse response;
        String tokenAndroid = "";
        UserEntity userEntity = null;
        ContestDetailEntity userWinner;
        ShopEntity shopEntity = shopDao.findByIdShop(terminateContestRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        TContestEntity tContestEntity = tContestDao.findByIdContest(terminateContestRequest.idContest).orElseThrow(() -> new ConflictException(ConstantsTexts.CONTEST_INVALID));
        PromoEntity promoEntity = promoDao.findByIdPromo(tContestEntity.idPromo).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        PromoEntity awardConsolation = promoDao.findById(promoEntity.awardConsolation).orElseThrow(() -> new ConflictException(ConstantsTexts.PROMO_INVALID));
        if (terminateContestRequest.containAudio) {
            //si contiene audio se obtiene el ganador con un random
            List<ContestDetailEntity> usersContest = contestDetailDao.findUsersContest(terminateContestRequest.idContest);
            userWinner = getUserRandomWinner(usersContest);
        } else {
            //si no contiene audio se obtiene al ganador del tickteo
            userWinner = contestDetailDao.findUserWinner(terminateContestRequest.idContest);
        }

        List<ContestDetailEntity> participatingUsers = contestDetailDao.findUsersContest(terminateContestRequest.idContest);
        WinnersEntity winnersEntity = new WinnersEntity();
        List<String> tokensDevices = new ArrayList<>();

        if (userWinner != null) {
            userEntity = userEntityDao.findByIdUser(userWinner.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));

            winnersEntity.idContest = terminateContestRequest.idContest;
            winnersEntity.idUser = userEntity.idUser;
            winnersEntity.idWinner = new GenerateUuid().generateUuid();
            winnersEntity.tickCount = userWinner.tickCount;
            winnersEntity.winningDate = new Date();
            winnersEntity.idShop = terminateContestRequest.idShop;
            winnersEntity.idBroadcaster = tContestEntity.getIdBroadcaster();
            winnersEntity.statusWinner = WinnersEntity.StatusWinner.Ganado;
            if (terminateContestRequest.containAudio) {
                winnersEntity.typeWinner = WinnersEntity.TypeWinner.ConAudio;
            } else {
                winnersEntity.typeWinner = WinnersEntity.TypeWinner.SinAudio;
            }
            tokenAndroid = userEntity.tokenAndroid == null ? "" : userEntity.tokenAndroid;
        }
        if (participatingUsers.size() > 0) {
            tokensDevices.add("");
            for (ContestDetailEntity p : participatingUsers) {
                UserEntity userEntityDaoById = userEntityDao.findById(p.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
                if (!userEntityDaoById.idUser.equals(userEntity != null ? userEntity.idUser : null)) {
                    String token = userEntityDaoById.tokenAndroid == null ? "" : userEntityDaoById.tokenAndroid;
                    tokensDevices.add(token);
                }
            }
        }
        tContestEntity.setStatusContest(TContestEntity.StatusContest.Terminado);
        try {
            if (userWinner != null) {
                winnersEntityDao.save(winnersEntity);
            }
            tContestDao.save(tContestEntity);
        } catch (TransactionSystemException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<TContestEntity>> violations = validator.validate(tContestEntity);
            for (final ConstraintViolation<TContestEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        try {
            if (userWinner != null) {
                NotificateWinner.sendPushNotification(tokenAndroid, ConstantsTexts.WINNER_TYPE, shopEntity.name);
                //si no contiene audio se debe contabilizar la notificacion
//                if (!terminateContestRequest.containAudio) {
//                    contabilizarNotifications(terminateContestRequest.typeNotify, terminateContestRequest.idShop);
//                }
            }
            if (tokensDevices.size() > 0) {
                NotificateLosers.sendNotificationsLosers(tokensDevices, ConstantsTexts.TICKTEAR_LOSERS, promoEntity.getAwardConsolation(), shopEntity.name, terminateContestRequest.idShop, awardConsolation.getImage(), shopEntity.image);
            }
        } catch (IOException e) {
            msg = ConstantsTexts.NOTIFICATE_ERROR + StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.info(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        msg = ConstantsTexts.TERMINATE_CONTEST;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void contabilizarNotifications(ShopNotificationsEntity.TypeNotify typeNotify, String idShop) {
        ShopNotificationsEntity shopNotificationsEntity = new ShopNotificationsEntity();
        shopNotificationsEntity.createdTime = new Date();
        shopNotificationsEntity.typeNotify = typeNotify;
        shopNotificationsEntity.uidShop = idShop;
        shopNotificationsDao.save(shopNotificationsEntity);
    }

    private static String encodeBase64(byte[] bstr) {
        Base64 base64 = new Base64();
        return new String(base64.encode(bstr));
    }


    private String encryptByHMACSHA1(byte[] data, byte[] key) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(key, "HmacSHA1");
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data);
            return encodeBase64(rawHmac);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getUTCTimeSeconds() {
        Calendar cal = Calendar.getInstance();
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal.getTimeInMillis() / 1000 + "";
    }


    private String postHttp(String url, Map<String, Object> postParams,
                            Map<String, String> headerParams, int timeout) {
        String result = null;

        if (postParams == null) {
            return result;
        }

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);

            if (headerParams != null) {
                for (String key : headerParams.keySet()) {
                    String value = headerParams.get(key);
                    httpPost.addHeader(key, value);
                }
            }

            MultipartEntityBuilder mEntityBuilder = MultipartEntityBuilder
                    .create();
            for (String key : postParams.keySet()) {
                Object value = postParams.get(key);
                if (value instanceof String || value instanceof Integer) {
                    ContentType contentType = ContentType.create("text/plain", "UTF-8");
                    StringBody stringBody = new StringBody(value + "", contentType);
                    mEntityBuilder.addPart(key, stringBody);
                } else if (value instanceof File) {
                    mEntityBuilder.addBinaryBody(key, (File) value);
                }
            }

            httpPost.setEntity(mEntityBuilder.build());

            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout).setSocketTimeout(timeout)
                    .build();
            httpPost.setConfig(requestConfig);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new ConflictException("Error: " + StringFormatUtilities.exceptionMsgBuilder(e));
            }
        }
        return result;
    }

    public String upload(String audioId, String audioTitle,
                         String dataType, String bucketName, String accessKey,
                         String accessSecret, MultipartFile file) {
        String result = null;
        String reqUrl = urlAudios;
        String htttMethod = "POST";
        String httpAction = "/v1/audios";
        String signatureVersion = "1";
        String timestamp = this.getUTCTimeSeconds();


        String sigStr = htttMethod + "\n" + httpAction + "\n" + accessKey
                + "\n" + signatureVersion + "\n" + timestamp;
        String signature = encryptByHMACSHA1(sigStr.getBytes(),
                accessSecret.getBytes());
        File audio = null;
        try {
            audio = multipartToFile(file, file.getName());
        } catch (IOException e) {
            throw new ConflictException("Error en el audio: " + StringFormatUtilities.exceptionMsgBuilder(e));
        }
        Map<String, String> headerParams = new HashMap<String, String>();
        headerParams.put("access-key", accessKey);
        headerParams.put("signature-version", signatureVersion);
        headerParams.put("signature", signature);
        headerParams.put("timestamp", timestamp);

        Map<String, Object> postParams = new HashMap<String, Object>();
        postParams.put("title", audioTitle);
        postParams.put("audio_id", audioId);
        postParams.put("bucket_name", bucketName);
        postParams.put("data_type", dataType);
        postParams.put("audio_file", audio);
        result = this.postHttp(reqUrl, postParams, headerParams, 8000);
        return result;
    }

    public static File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        multipart.transferTo(convFile);
        return convFile;
    }
}
