package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.UserBusiness;
import com.americadigital.libupapi.Dao.Entity.UserEntity;
import com.americadigital.libupapi.Dao.Interfaces.States.StatesDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.User.*;
import com.americadigital.libupapi.Security.JWT.Configuration.JwtTokenUtil;
import com.americadigital.libupapi.Security.JWT.Service.JwtUserDetailsService;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.User.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.User.*;
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
import java.util.Set;

@Service("userBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class UserBusinessImpl implements UserBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(UserBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_PROFILE_USER + "/";

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private UserPagDao userPagDao;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private StatesDao statesDao;

    @Override
    public ResponseEntity<UserLoginResponse> loginUser(UserLogginRequest loginRequest) {
        UserLoginResponse response;
        String msg = "";
        UserEntity user;
        user = userEntityDao.findByEmail(loginRequest.email).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_INVALID));
        if (user.accountType.equals(UserEntity.AccountType.Normal)) {
            if (!user.password.equals(loginRequest.password)) {
                msg = ConstantsTexts.USER_INVALID;
                LOG.info(msg);
                throw new ConflictException(msg);
            }
        }
        if (!user.active || user.isDeleted) {
            msg = ConstantsTexts.USER_NOT_ACTIVE;
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        if (loginRequest.operativeSystem.equals(UserEntity.OperativeSystem.Android)) {
            user.tokenAndroid = loginRequest.tokenDevice;
        } else if (loginRequest.operativeSystem.equals(UserEntity.OperativeSystem.Ios)) {
            user.tokenIos = loginRequest.tokenDevice;
        }
        try {
            userEntityDao.save(user);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
            for (final ConstraintViolation<UserEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.USER_VALID;
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginRequest.email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        response = new UserLoginResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Login(user.idUser, user.name, user.lastName, token, user.idState));
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshRequest refreshRequest) {
        String msg;
        RefreshTokenResponse response;
        UserEntity userEntity = userEntityDao.findByEmail(refreshRequest.email).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        if (!userEntity.active || userEntity.isDeleted) {
            msg = ConstantsTexts.USER_NOT_ACTIVE;
            LOG.info(msg);
            throw new ConflictException(msg);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(refreshRequest.email);
        final String token = jwtTokenUtil.generateToken(userDetails);
        msg = ConstantsTexts.JWT_GET;
        response = new RefreshTokenResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Token(userEntity.email, token));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRegisterResponse> registerUser(UserRegisterRequest registerRequest) {
        UserRegisterResponse response;
        String extension = "";
        String msg = "";
        GenerateUuid uuid;
        statesDao.findById(registerRequest.idState).orElseThrow(()-> new ConflictException(ConstantsTexts.STATE_INVALID));
        UserEntity userEntity;
        uuid = new GenerateUuid();
        userEntity = new UserEntity();
        userEntity.idUser = uuid.generateUuid();
        userEntity.name = registerRequest.getName();
        userEntity.lastName = registerRequest.getLastName();
        userEntity.email = registerRequest.getEmail();
        if (registerRequest.getOperativeSystem().equals(UserEntity.OperativeSystem.Android)) {
            userEntity.tokenAndroid = registerRequest.getTokenDevice();
        } else if (registerRequest.getOperativeSystem().equals(UserEntity.OperativeSystem.Ios)) {
            userEntity.tokenIos = registerRequest.getTokenDevice();
        }

        if (registerRequest.getPassword().isPresent() && registerRequest.getPassword().get().length() > 0) {
            userEntity.password = registerRequest.getPassword().get();
        }
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        //foto de perfil
        if (registerRequest.getProfilePicture().isPresent() && registerRequest.getProfilePicture().get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(registerRequest.getProfilePicture().get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(registerRequest.getProfilePicture().get());
                userEntity.name_profile = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        if (registerRequest.getPhoneNumber().isPresent() && registerRequest.getPhoneNumber().get().length() > 0) {
            userEntity.phoneNumber = registerRequest.getPhoneNumber().get();
        }
        userEntity.active = true;
        if (registerRequest.getSocialId().isPresent() && registerRequest.getSocialId().get().length() > 0) {
            userEntity.socialId = registerRequest.getSocialId().get();
        }
        userEntity.accountType = registerRequest.getAccountType();
        userEntity.isDeleted = false;
        userEntity.idState = registerRequest.idState;
        UserEntity save;
        try {
            save = userEntityDao.save(userEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
            for (final ConstraintViolation<UserEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.USER_ADD;
        response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Register(save.idUser));
        if (registerRequest.getProfilePicture().isPresent() && registerRequest.getProfilePicture().get().length() > 0) {
            SaveImages saveImages = new SaveImages();
            try {
                saveImages.saveImages(registerRequest.getProfilePicture().get(), IMAGE_FOLDER, nameImage, extension);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllUserResponse> getAllUsers(AllUserRequest userRequest) {
        String msg;
        GetAllUserResponse response;
        Page<UserEntity> allUsers = userPagDao.findAllUsers(PageRequest.of(userRequest.page, userRequest.maxResults));
        List<UserAll> listUsers;
        if (allUsers.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllUserResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        listUsers = new ArrayList<>();
        allUsers.forEach(u -> listUsers.add(new UserAll(u.idUser, u.name, u.lastName, u.email, u.phoneNumber, u.active, u.name_profile, u.accountType)));
        msg = ConstantsTexts.USER_LIST;
        response = new GetAllUserResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), listUsers, allUsers.getTotalPages(), allUsers.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetAllUserResponse> searchUsers(SearchRequest searchRequest) {
        String msg;
        GetAllUserResponse response;
        Page<UserEntity> allUsers = userPagDao.findUsersByAllColumns(searchRequest.searchText, PageRequest.of(searchRequest.page, Integer.MAX_VALUE));
        List<UserAll> listUsers;
        if (allUsers.isEmpty()) {
            msg = ConstantsTexts.EMPTY_LIST;
            LOG.info(msg);
            response = new GetAllUserResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        listUsers = new ArrayList<>();
        allUsers.forEach(u -> listUsers.add(new UserAll(u.idUser, u.name, u.lastName, u.email, u.phoneNumber, u.active, u.name_profile, u.accountType)));
        msg = ConstantsTexts.USER_LIST;
        response = new GetAllUserResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), listUsers, allUsers.getTotalPages(), allUsers.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<GetUserByIdResponse> getUserById(String idUser) {
        String msg;
        GetUserByIdResponse response;
        UserEntity userEntity = userEntityDao.findByIdUser(idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        msg = ConstantsTexts.USER_GET;
        response = new GetUserByIdResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new UserGet(userEntity.idUser, userEntity.name, userEntity.lastName, userEntity.email, userEntity.phoneNumber, userEntity.active, userEntity.name_profile, userEntity.accountType));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRegisterResponse> updateUser(UserUpdateRequest updateRequest) {
        UserRegisterResponse response;
        String extension = "";
        String msg = "";
        UserEntity userEntity;
        String profile;
        userEntity = userEntityDao.findByIdUser(updateRequest.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        profile = userEntity.name_profile;
        userEntity.name = updateRequest.name;
        userEntity.lastName = updateRequest.lastName;
        userEntity.email = updateRequest.email;
        GenerateNameImg generateNameImg = new GenerateNameImg();
        String nameImage = generateNameImg.generateUuidImage();
        if (updateRequest.profilePicture.isPresent() && updateRequest.profilePicture.get().length() > 0) {
            DecodeBase64 decodeBase64 = new DecodeBase64();
            BufferedImage bufferedImage = decodeBase64.decodeToImage(updateRequest.profilePicture.get());
            if (bufferedImage != null) {
                extension = GetExtensions.getExtensionImage(updateRequest.profilePicture.get());
                userEntity.name_profile = nameImage + extension;
            } else {
                msg = ConstantsTexts.FORMAT_BASE64;
                throw new ConflictException(msg);
            }
        }
        if (updateRequest.phoneNumber.isPresent() && updateRequest.phoneNumber.get().length() > 0) {
            userEntity.phoneNumber = updateRequest.phoneNumber.get();
        }
        UserEntity save;
        try {
            save = userEntityDao.save(userEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
            for (final ConstraintViolation<UserEntity> violation : violations) {
                msg += violation.getMessage() + " ";
            }
            LOG.error(msg);
            throw new ConflictException(msg);
        }
        msg = ConstantsTexts.USER_UPDATE;
        response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new Register(save.idUser));
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
        UserEntity userEntity = userEntityDao.findByIdUser(request.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        userEntity.active = request.active;
        userEntityDao.save(userEntity);
        msg = ConstantsTexts.ACTIVE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> deleteUser(String idUser) {
        String msg;
        HeaderResponse response;
        UserEntity userEntity = userEntityDao.findByIdUser(idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        userEntity.isDeleted = true;
        userEntityDao.save(userEntity);
        msg = ConstantsTexts.USER_DELETE;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> changePassword(ChangePasswordRequest request) {
        String msg = "";
        HeaderResponse response;
        UserEntity userEntity = userEntityDao.findByIdUser(request.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        if (!userEntity.password.equals(request.currentPassword)) {
            msg = ConstantsTexts.PASS_INVALID;
            throw new ConflictException(msg);
        }
        userEntity.password = request.newPassword;
        try {
            userEntityDao.save(userEntity);
        } catch (TransactionException e) {
            final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
            final Validator validator = validatorFactory.getValidator();
            final Set<ConstraintViolation<UserEntity>> violations = validator.validate(userEntity);
            for (final ConstraintViolation<UserEntity> violation : violations) {
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
    public ResponseEntity<HeaderResponse> updateCoordinates(UserCoordinatesRequest request) {
        String msg;
        HeaderResponse response;
        UserEntity userEntity = userEntityDao.findById(request.idUser).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        userEntity.latitude = request.latitude;
        userEntity.longitude = request.longitude;
        userEntityDao.save(userEntity);
        msg = ConstantsTexts.USER_COORDINATES;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
