package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.UserAdminBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.UserAdmin.ChangePasswordAdminRequest;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/userAdmin")
public class UserAdminController {
    private static final Logger LOG = LoggerFactory.getLogger(UserAdminController.class);

    @Autowired
    private UserAdminBusiness adminBusiness;

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserLoginAdminResponse> loginAdmin(@RequestBody final UserAdminLogginRequest request) {
        String msg;
        UserLoginAdminResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /userAdmin/login Request: " + request + " at " + new Date());
            return adminBusiness.loginUserAdmin(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new UserLoginAdminResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new UserLoginAdminResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserRegisterResponse> addUserAdmin(@Valid @RequestBody final UserAdminRegisterRequest request) {
        String msg;
        UserRegisterResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/userAdmin/add Request: " + request + " at " + new Date());
            return adminBusiness.registerUser(request);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.USER_DUPLICATE;
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/getAll",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllUserAdminResponse> getAllUsersAdmin(@Valid @RequestBody AllUserRequest userRequest) {
        String msg;
        GetAllUserAdminResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/userAdmin/getAll Request: " + " at " + new Date());
            return adminBusiness.getAllUsersAdmin(userRequest);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null, 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllUserAdminResponse> getAllUsersAdmin(@Valid @RequestBody SearchRequest request) {
        String msg;
        GetAllUserAdminResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/userAdmin/search Request: " + " at " + new Date());
            return adminBusiness.searchUsersAdmin(request);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllUserAdminResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null, 0, 0L);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getById",
            method = RequestMethod.GET)
    public ResponseEntity<GetUserAdminByIdResponse> getUserAdmiById(@RequestParam(value = "idAdmin") String idAdmin) {
        String msg;
        GetUserAdminByIdResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /v1/userAdmin/getById Request: " + idAdmin + " at " + new Date());
            return adminBusiness.getUserById(idAdmin);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetUserAdminByIdResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetUserAdminByIdResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserRegisterResponse> updateUserAdmin(@Valid @RequestBody final UserAdminUpdateRequest request) {
        String msg;
        UserRegisterResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /v1/userAdmin/update Request: " + request + " at " + new Date());
            return adminBusiness.updateUserAdmin(request);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.USER_DUPLICATE;
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new UserRegisterResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changeStatusUser(@Valid @RequestBody final ChangeStatusUserRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /v1/userAdmin/status Request: " + request + " at " + new Date());
            return adminBusiness.changeStatus(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE)
    public ResponseEntity<HeaderResponse> deleteUser(@Valid @RequestParam(value = "idAdmin") String idAdmin) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry DELETE Method: /v1/userAdmin/delete Request: " + idAdmin + " at " + new Date());
            return adminBusiness.deleteUser(idAdmin);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/changePassword",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changePasswordAdmin(@Valid @RequestBody final ChangePasswordAdminRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /v1/userAdmin/changePassword Request: " + request + " at " + new Date());
            return adminBusiness.changePassword(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/updatePassword",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> updatePassBySuperAdmin(@Valid @RequestBody final ChangePassRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /v1/userAdmin/updatePassword Request: " + request + " at " + new Date());
            return adminBusiness.changePasswordAdmin(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/verifyPlan",
            method = RequestMethod.GET)
    public ResponseEntity<PlanActiveResponse> verifyPlan(@RequestParam(value = "idShop") String idShop) {
        String msg;
        PlanActiveResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /v1/userAdmin/verifyPlan Request: " + idShop + " at " + new Date());
            return adminBusiness.verifyPlanActive(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new PlanActiveResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new PlanActiveResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
