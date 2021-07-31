package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.BroadcasterBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/broadcaster")
public class BroadcasterController {
    private static final Logger LOG = LoggerFactory.getLogger(BroadcasterController.class);

    @Autowired
    private BroadcasterBusiness broadcasterBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addBroadcaster(@Valid @RequestBody AddBroadcasterRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /broadcaster/add Request: " + request + " at " + new Date());
            return broadcasterBusiness.addBroadcaster(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.BROADCASTER_DUPLICATE;
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

    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> updateBroadcaster(@Valid @RequestBody UpdateBroadcasterRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /broadcaster/update Request: " + request + " at " + new Date());
            return broadcasterBusiness.updateBroadcaster(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.BROADCASTER_DUPLICATE;
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

    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changeStatusBroadcaster(@Valid @RequestBody StatusBroadcasterRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /broadcaster/status Request: " + request + " at " + new Date());
            return broadcasterBusiness.changeStatus(request);
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

    @RequestMapping(value = "/getById",
            method = RequestMethod.GET)
    public ResponseEntity<BroadcasterResponse> getBroadcasterById(@RequestParam("idBroadcaster") String idBroadcaster) {
        String msg;
        BroadcasterResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /Broadcaster/getById Request: " + idBroadcaster + " at " + new Date());
            return broadcasterBusiness.getBroadcasterById(idBroadcaster);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BroadcasterResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BroadcasterResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BroadcasterListResponse> getBroadcasterList(@Valid @RequestBody AllBroadcasterRequest request) {
        String msg;
        BroadcasterListResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /Broadcaster/getAll Request: " + request + " at " + new Date());
            return broadcasterBusiness.getBroadcasterListResponse(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAllActives",
            method = RequestMethod.GET)
    public ResponseEntity<BroadcasterListResponse> getBroadcasterListActives() {
        String msg;
        BroadcasterListResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /Broadcaster/getAllActives Request: "  + " at " + new Date());
            return broadcasterBusiness.getBroadcasterListActives();
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BroadcasterListResponse> searchBroadcasterList(@Valid @RequestBody SearchBroadcasterRequest request) {
        String msg;
        BroadcasterListResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /Broadcaster/search Request: " + request + " at " + new Date());
            return broadcasterBusiness.searchBroadcaster(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new BroadcasterListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
