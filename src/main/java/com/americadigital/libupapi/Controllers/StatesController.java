package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.StatesBusiness;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Response.States.StatesResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/states")
public class StatesController {
    private static final Logger LOG = LoggerFactory.getLogger(StatesController.class);

    @Autowired
    private StatesBusiness statesBusiness;

    @RequestMapping(value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<StatesResponse> getAllStates() {
        String msg;
        StatesResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /states/get Request: " + " at " + new Date());
            return statesBusiness.getAllStates();
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new StatesResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
