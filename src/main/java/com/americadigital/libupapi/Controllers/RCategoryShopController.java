package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.RCategoryBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.RCategory.RCategoryRequest;
import com.americadigital.libupapi.WsPojos.Response.RCategory.AddRCategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.RCategory.RCategoryGetResponse;
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
@RequestMapping("/api/v1/rCategory")
public class RCategoryShopController {
    private static final Logger LOG = LoggerFactory.getLogger(RCategoryShopController.class);

    @Autowired
    private RCategoryBusiness rCategoryBusiness;

    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AddRCategoryResponse> addOrUpdateRelation(@Valid @RequestBody RCategoryRequest request) {
        AddRCategoryResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /rCategory/update Request: " + request + " at " + new Date());
            return rCategoryBusiness.addOrUpdateRelation(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new AddRCategoryResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
        catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new AddRCategoryResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findAll",
            method = RequestMethod.GET)
    public ResponseEntity<RCategoryGetResponse> getRelations(@RequestParam(value = "idShop") String idShop) {
        RCategoryGetResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /rCategory/findAll Request: " + idShop + " at " + new Date());
            return rCategoryBusiness.getListRelationsCategories(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new RCategoryGetResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new RCategoryGetResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
