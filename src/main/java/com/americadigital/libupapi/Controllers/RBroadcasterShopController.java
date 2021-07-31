package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.RBroadcasterShopBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Promo.AddOrRemoveRelationRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.AddRelationBroadcasterShopRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.GetRelationsMatchRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.RBroadcasterShop.GetAllRBroadcasterShopResponse;
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
@RequestMapping("/api/v1/radios/shop")
public class RBroadcasterShopController {

    private static final Logger LOG = LoggerFactory.getLogger(RBroadcasterShopController.class);

    @Autowired
    private RBroadcasterShopBusiness broadcasterShopBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addBroadcasterShop(@Valid @RequestBody AddRelationBroadcasterShopRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/add Request: " + request + " at " + new Date());
            return broadcasterShopBusiness.addBroadcasterToShop(request);
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
    public ResponseEntity<HeaderResponse> deleteRelation(@RequestParam(value = "idRelation") String idRelation) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/delete Request: " + idRelation + " at " + new Date());
            return broadcasterShopBusiness.deleteRelationBroadcasterShop(idRelation);
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

    @RequestMapping(value = "/getRelations",
            method = RequestMethod.GET)
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsByIdShop(@RequestParam(value = "idShop") String idShop) {
        GetAllRBroadcasterShopResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/getRelations Request: " + idShop + " at " + new Date());
            return broadcasterShopBusiness.getAllRelationsBroadcasterShop(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getRelationsActives",
            method = RequestMethod.GET)
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsBroadcasterShopsActives(@RequestParam(value = "idShop") String idShop) {
        GetAllRBroadcasterShopResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/getRelationsActives Request: " + idShop + " at " + new Date());
            return broadcasterShopBusiness.getRelationsBroadcasterShopsActives(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getRelationsByIdBroadcaster",
            method = RequestMethod.POST)
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsByIdBroadcaster(@Valid @RequestBody GetRelationsMatchRequest request) {
        GetAllRBroadcasterShopResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/getRelationsByIdBroadcaster Request: " + request + " at " + new Date());
            return broadcasterShopBusiness.getRelationsByIdBroadcaster(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getRelationsActivesByIdBroadcaster",
            method = RequestMethod.GET)
    public ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsActivesByIdBroadcaster(@RequestParam(value = "idBroadcaster") String idBroadcaster) {
        GetAllRBroadcasterShopResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/shop/getRelationsActivesByIdBroadcaster Request: " + idBroadcaster + " at " + new Date());
            return broadcasterShopBusiness.getRelationsActivesByIdBroadcaster(idBroadcaster);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllRBroadcasterShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/updateContract",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> updateContract(@Valid @RequestBody AddOrRemoveRelationRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /radios/updateContract Request: " + request + " at " + new Date());
            return broadcasterShopBusiness.addOrRemoveRelation(request);
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
}
