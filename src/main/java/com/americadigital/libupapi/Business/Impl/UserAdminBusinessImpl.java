package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.UserAdminBusiness;
import com.americadigital.libupapi.Dao.Entity.*;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminPagDao;
import com.americadigital.libupapi.Dao.Interfaces.Branch.BranchDao;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.ShopNotifications.ShopNotificationsDao;
import com.americadigital.libupapi.Dao.Interfaces.ShopSubscription.ShopSubscriptionsDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.User.Register;
import com.americadigital.libupapi.Pojos.UserAdmin.ActivePlan;
import com.americadigital.libupapi.Pojos.UserAdmin.ChangePasswordAdminRequest;
import com.americadigital.libupapi.Pojos.UserAdmin.LoginAdmin;
import com.americadigital.libupapi.Pojos.UserAdmin.UserAdminGet;
import com.americadigital.libupapi.Security.JWT.Configuration.JwtTokenUtil;
import com.americadigital.libupapi.Security.JWT.Service.JwtUserDetailsService;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.User.AllUserRequest;
import com.americadigital.libupapi.WsPojos.Request.User.ChangeStatusUserRequest;
import com.americadigital.libupapi.WsPojos.Request.User.SearchRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.ChangePassRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminLogginRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminRegisterRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminUpdateRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.User.UserRegisterResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.GetAllUserAdminResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.GetUserAdminByIdResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.PlanActiveResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.UserLoginAdminResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service("userAdminBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class UserAdminBusinessImpl implements UserAdminBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(UserAdminBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_PROFILE_ADMIN + "/";

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private UserAdminPagDao userAdminPagDao;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BranchDao branchDao;

    @Autowired
    private BroadcasterDao broadcasterDao;

    @Autowired
    private ShopSubscriptionsDao shopSubscriptionsDao;

    @Autowired
    private ShopNotificationsDao shopNotificationsDao;


    @Override
    public ResponseEntity<UserLoginAdminResponse> loginUserAdmin(UserAdminLogginRequest loginRequest) {
        UserLoginAdminResponse response;
        String msg;
        UserAdminEntity user;
        int usedNotfs = 0;
        boolean subscriptionActive = false;
        String latitude = "21.501062";
        String longitude = "-104.9119241";
        user = userAdminDao.findByEmail(loginRequest.email).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_INVALID));
        List<BranchEntity> activesBranchesByIdShop = branchDao.findActivesBranchesByIdShop(user.idShop);
        for (BranchEntity branchEntity : activesBranchesByIdShop) {
            if (branchEntity.type.toString().equals("Principal")) {
                latitude = branchEntity.latitud;
                longitude = branchEntity.longitud;
                break;
            }
        }
        if (user.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio)) {
            ShopEntity shopEntity = shopDao.findByIdShop(user.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
            if (!shopEntity.active || shopEntity.isDeleted) {
                msg = ConstantsTexts.USER_COMMERCE;
                throw new ConflictException(msg);
            }
        }

        if (user.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio)) {
            BroadcasterEntity broadcasterEntity = broadcasterDao.findById(user.idBroadcaster).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
            if (!broadcasterEntity.active || broadcasterEntity.isDeleted) {
                msg = ConstantsTexts.USER_BROADCASTER;
                throw new ConflictException(msg);
            }

        }
        if (!user.password.equals(loginRequest.password)) {
            msg = ConstantsTexts.USER_INVALID;
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        if (!user.active || user.isDeleted) {
            msg = ConstantsTexts.USER_NOT_ACTIVE;
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        //obtener la ultima subscripcion por medio del id autoincrement
        Optional<ShopSubscriptionEntity> theLastSubscription = shopSubscriptionsDao.getTheLastSubscription(user.idShop);

        if (theLastSubscription.isPresent()) {
            //Obtener el numero de notificaciones usadas
            List<ShopNotificationsEntity> notificationsUsed = shopNotificationsDao.getNotificationsUsed(user.idShop, theLastSubscription.get().createdTime, theLastSubscription.get().expirationDate);
            usedNotfs = notificationsUsed.size();
            //verificar si la suscripcion esta activa
            Optional<ShopSubscriptionEntity> shopSubscriptionEntity = shopSubscriptionsDao.checkActiveSubscriptionCommerce(theLastSubscription.get().id_suscription);
            subscriptionActive = shopSubscriptionEntity.isPresent();
        }
        msg = ConstantsTexts.USER_VALID;
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        response = new UserLoginAdminResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new LoginAdmin(user.idAdmin, user.name, user.lastName, user.typeAdmin, token, user.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) || user.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : user.idShop, user.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) || user.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : user.idBroadcaster, user.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) || user.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : user.shopEntity.name, latitude, longitude,
                subscriptionActive, theLastSubscription.map(shopSubscriptionEntity -> shopSubscriptionEntity.notificationsAllowed).orElse(0), usedNotfs, theLastSubscription.map(subscriptionEntity -> ConstantsTexts.onlyDate.format((subscriptionEntity.expirationDate))).orElse("")));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRegisterResponse> registerUser(UserAdminRegisterRequest adminRegisterRequest) {
        UserRegisterResponse response;
        String extension = "";
        String msg = "";
        GenerateUuid uuid;
        UserAdminEntity userEntity;
        uuid = new GenerateUuid();
        userEntity = new UserAdminEntity();
        userEntity.idAdmin = uuid.generateUuid();
        userEntity.name = adminRegisterRequest.getName();
        userEntity.lastName = adminRegisterRequest.getLastName();
        userEntity.email = adminRegisterRequest.getEmail();
        userEntity.password = adminRegisterRequest.getPassword();
        if (adminRegisterRequest.getIdShop().isPresent() && adminRegisterRequest.getIdShop().get().length() > 0) {
            shopDao.findByIdShop(adminRegisterRequest.getIdShop().get()).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
            userEntity.idShop = adminRegisterRequest.getIdShop().get();
        }
        if (adminRegisterRequest.getIdBroadcaster().isPresent() && adminRegisterRequest.getIdBroadcaster().get().length() > 0) {
            broadcasterDao.findById(adminRegisterRequest.getIdBroadcaster().get()).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
            userEntity.idBroadcaster = adminRegisterRequest.getIdBroadcaster().get();
        }
        if (adminRegisterRequest.getPhoneNumber().isPresent() && adminRegisterRequest.getPhoneNumber().get().length() > 0) {
            userEntity.phoneNumber = adminRegisterRequest.getPhoneNumber().get();
        }
        userEntity.active = true;
        userEntity.typeAdmin = adminRegisterRequest.getTypeAdmin();
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        //foto de perfil
        if (adminRegisterRequest.getProfilePicture().isPresent() && adminRegisterRequest.getProfilePicture().get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(adminRegisterRequest.getProfilePicture().get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(adminRegisterRequest.getProfilePicture().get());
                userEntity.profilePicture = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        UserAdminEntity save;
        try {
            save = userAdminDao.save(userEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserAdminEntity>> violations = validator.validate(userEntity);
            for (final ConstraintViolation<UserAdminEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.USER_ADD;
        response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Register(save.idAdmin));
        if (adminRegisterRequest.getProfilePicture().isPresent() && adminRegisterRequest.getProfilePicture().get().length() > 0) {
            SaveImages saveImages = new SaveImages();
            try {
                saveImages.saveImages(adminRegisterRequest.getProfilePicture().get(), IMAGE_FOLDER, nameImage, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllUserAdminResponse> getAllUsersAdmin(AllUserRequest userRequest) {
        String msg;
        GetAllUserAdminResponse response;
        List<UserAdminGet> userAdmins;
        Page<UserAdminEntity> allUsersAdmin = userAdminPagDao.findAllUsersAdmin(PageRequest.of(userRequest.page, userRequest.maxResults));
        if (allUsersAdmin.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        userAdmins = new ArrayList<>();
        allUsersAdmin.forEach(u -> userAdmins.add(new UserAdminGet(u.idAdmin, u.name, u.lastName, u.email, u.phoneNumber, u.active, u.typeAdmin, u.profilePicture, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) ? null : u.shopEntity.idShop, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) ? null : u.shopEntity.name, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) ? null : u.idBroadcaster, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) ? null : u.broadcasterEntity.name)));
        msg = ConstantsTexts.USER_LIST;
        response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), userAdmins, allUsersAdmin.getTotalPages(), allUsersAdmin.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllUserAdminResponse> searchUsersAdmin(SearchRequest searchRequest) {
        String msg;
        GetAllUserAdminResponse response;
        List<UserAdminGet> userAdmins;
        Page<UserAdminEntity> allUsersAdmin = userAdminPagDao.findUsersAdminByAllColumns(searchRequest.searchText, PageRequest.of(searchRequest.page, Integer.MAX_VALUE));
        if (allUsersAdmin.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        userAdmins = new ArrayList<>();
        allUsersAdmin.forEach(u -> userAdmins.add(new UserAdminGet(u.idAdmin, u.name, u.lastName, u.email, u.phoneNumber, u.active, u.typeAdmin, u.profilePicture, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) ? null : u.shopEntity.idShop, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) ? null : u.shopEntity.name, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) ? null : u.idBroadcaster, u.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) ? null : u.broadcasterEntity.name)));
        msg = ConstantsTexts.USER_LIST;
        response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), userAdmins, allUsersAdmin.getTotalPages(), allUsersAdmin.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetUserAdminByIdResponse> getUserById(String idUser) {
        String msg;
        GetUserAdminByIdResponse response;
        UserAdminEntity userAdminEntity = userAdminDao.findByIdAdmin(idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        msg = ConstantsTexts.USER_GET;
        response = new GetUserAdminByIdResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new UserAdminGet(userAdminEntity.idAdmin, userAdminEntity.name, userAdminEntity.lastName, userAdminEntity.email, userAdminEntity.phoneNumber, userAdminEntity.active, userAdminEntity.typeAdmin, userAdminEntity.profilePicture, userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) || userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : userAdminEntity.shopEntity.idShop, userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.Radio) || userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : userAdminEntity.shopEntity.name, userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) || userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : userAdminEntity.idBroadcaster, userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.Comercio) || userAdminEntity.typeAdmin.equals(UserAdminEntity.TypeAdmin.SuperAdmin) ? null : userAdminEntity.broadcasterEntity.name));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRegisterResponse> updateUserAdmin(UserAdminUpdateRequest updateRequest) {
        UserRegisterResponse response;
        String extension = "";
        String msg = "";
        UserAdminEntity userEntity;
        String profile;
        userEntity = userAdminDao.findByIdAdmin(updateRequest.idAdmin).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        profile = userEntity.profilePicture;
        userEntity.name = updateRequest.name;
        userEntity.lastName = updateRequest.lastName;
        userEntity.email = updateRequest.email;
        if (updateRequest.idShop.isPresent() && updateRequest.idShop.get().length() > 0) {
            shopDao.findByIdShop(updateRequest.idShop.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
            userEntity.idShop = updateRequest.idShop.get();
        }
        if (updateRequest.idBroadcaster.isPresent() && updateRequest.idBroadcaster.get().length() > 0) {
            broadcasterDao.findById(updateRequest.idBroadcaster.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
            userEntity.idBroadcaster = updateRequest.idBroadcaster.get();
        }
        if (updateRequest.phoneNumber.isPresent() && updateRequest.phoneNumber.get().length() > 0) {
            userEntity.phoneNumber = updateRequest.phoneNumber.get();
        }
        userEntity.typeAdmin = updateRequest.typeAdmin;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        //foto de perfil
        if (updateRequest.profilePicture.isPresent() && updateRequest.profilePicture.get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updateRequest.profilePicture.get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(updateRequest.profilePicture.get());
                userEntity.profilePicture = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }

        UserAdminEntity save;
        try {
            save = userAdminDao.save(userEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserAdminEntity>> violations = validator.validate(userEntity);
            for (final ConstraintViolation<UserAdminEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.USER_UPDATE;
        response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Register(save.idAdmin));
        if (updateRequest.profilePicture.isPresent() && updateRequest.profilePicture.get().length() > 0) {
            SaveImages saveImages = new SaveImages();
            DeleteImages delImg = new DeleteImages();
            try {
                saveImages.saveImages(updateRequest.profilePicture.get(), IMAGE_FOLDER, nameImage, extension);
                String msgImage = delImg.deleteImage(IMAGE_FOLDER + profile);
                LOG.error("Error: " + msgImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changeStatus(ChangeStatusUserRequest request) {
        String msg;
        HeaderResponse response;
        UserAdminEntity adminEntity = userAdminDao.findByIdAdmin(request.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        adminEntity.active = request.active;
        userAdminDao.save(adminEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteUser(String idUser) {
        String msg;
        HeaderResponse response;
        UserAdminEntity userAdminEntity = userAdminDao.findByIdAdmin(idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        userAdminEntity.isDeleted = true;
        userAdminDao.save(userAdminEntity);
        msg = ConstantsTexts.USER_DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changePassword(ChangePasswordAdminRequest request) {
        String msg = "";
        HeaderResponse response;
        UserAdminEntity adminEntity = userAdminDao.findByIdAdmin(request.idAdmin).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        if (!adminEntity.password.equals(request.currentPassword)) {
            msg = ConstantsTexts.PASS_INVALID;
            throw new ConflictException(msg);
        }
        adminEntity.password = request.newPassword;
        try {
            userAdminDao.save(adminEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserAdminEntity>> violations = validator.validate(adminEntity);
            for (final ConstraintViolation<UserAdminEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.PASS_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changePasswordAdmin(ChangePassRequest request) {
        String msg = "";
        HeaderResponse response;
        UserAdminEntity adminEntity = userAdminDao.findByIdAdmin(request.idAdmin).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BYID));
        adminEntity.password = request.newPassword;
        try {
            userAdminDao.save(adminEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserAdminEntity>> violations = validator.validate(adminEntity);
            for (final ConstraintViolation<UserAdminEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.PASS_UPDATE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PlanActiveResponse> verifyPlanActive(String idCommerce) {
        String msg;
        PlanActiveResponse response;
        int usedNotfs = 0;
        boolean subscriptionActive = false;
        //obtener la ultima subscripcion por medio del id autoincrement
        Optional<ShopSubscriptionEntity> theLastSubscription = shopSubscriptionsDao.getTheLastSubscription(idCommerce);
        if (theLastSubscription.isPresent()) {
            //Obtener el numero de notificaciones usadas
            List<ShopNotificationsEntity> notificationsUsed = shopNotificationsDao.getNotificationsUsed(idCommerce, theLastSubscription.get().createdTime, theLastSubscription.get().expirationDate);
            usedNotfs = notificationsUsed.size();
            //verificar si la suscripcion esta activa
            Optional<ShopSubscriptionEntity> shopSubscriptionEntity = shopSubscriptionsDao.checkActiveSubscriptionCommerce(theLastSubscription.get().id_suscription);
            subscriptionActive = shopSubscriptionEntity.isPresent();
        }
        msg = ConstantsTexts.SHOP_PLAN;
        response = new PlanActiveResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ActivePlan(
                subscriptionActive, theLastSubscription.map(shopSubscriptionEntity -> shopSubscriptionEntity.notificationsAllowed).orElse(0), usedNotfs, theLastSubscription.map(subscriptionEntity -> ConstantsTexts.onlyDate.format((subscriptionEntity.expirationDate))).orElse("")));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
