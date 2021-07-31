package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.BranchBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Branch.AddBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.CoordsRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.StatusBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.UpdateBranchRequest;
import com.americadigital.libupapi.WsPojos.Response.Branch.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {
    private static final Logger LOG = LoggerFactory.getLogger(BranchController.class);

    @Autowired
    private BranchBusiness branchBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BranchResponse> addBranch(@Valid @RequestBody AddBranchRequest request) {
        BranchResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /branch/add Request: " + request + " at " + new Date());
            return branchBusiness.addBranch(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BranchResponse> updateBranch(@Valid @RequestBody UpdateBranchRequest request) {
        BranchResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /branch/update Request: " + request + " at " + new Date());
            return branchBusiness.updateBranch(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BranchResponse> changeStatusBranch(@Valid @RequestBody StatusBranchRequest request) {
        BranchResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /branch/status Request: " + request + " at " + new Date());
            return branchBusiness.changeStatus(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/getById",
            method = RequestMethod.GET)
    public ResponseEntity<BranchGetResponse> getBranchById(@RequestParam(value = "idBranch") String idBranch) {
        BranchGetResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /branch/getById Request: " + idBranch + " at " + new Date());
            return branchBusiness.getBranchById(idBranch);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchGetResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchGetResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/getAll",
            method = RequestMethod.GET)
    public ResponseEntity<BranchListResponse> getAllBranchesAdmin(@RequestParam(value = "idShop") String idShop) {
        BranchListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /branch/getAll Request: " + idShop + " at " + new Date());
            return branchBusiness.getAllBranchesForAdmin(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findAll",
            method = RequestMethod.GET)
    public ResponseEntity<BranchActivesListResponse> getAllBranchesActives(@RequestParam(value = "idShop") String idShop) {
        BranchActivesListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /branch/findAll Request: " + idShop + " at " + new Date());
            return branchBusiness.getAllBranches(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BranchActivesListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BranchActivesListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE)
    public ResponseEntity<HeaderResponse> deleteBranch(@RequestParam(value = "idBranch") String idBranch) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry DELETE Method: /branch/delete Request: " + idBranch + " at " + new Date());
            return branchBusiness.deleteBranch(idBranch);
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


    @RequestMapping(value = "/coordinates/commerce",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CoordenadasResponse> getNearestShop(@Valid @RequestBody CoordsRequest request) {
        CoordenadasResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /coordinates/commerce Request: " + request + " at " + new Date());
            return branchBusiness.getNearestStore(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CoordenadasResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CoordenadasResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
