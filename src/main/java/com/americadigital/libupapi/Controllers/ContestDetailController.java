package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.ContestDetailBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.ContestDetail.ContestDetailRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/contestDetail")
public class ContestDetailController {
    private static final Logger LOG = LoggerFactory.getLogger(ContestDetailController.class);

    @Autowired
    private ContestDetailBusiness contestDetailBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addDetailContest(@RequestBody final ContestDetailRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /category/add Request: " + request + " at " + new Date());
            return contestDetailBusiness.addContestDetail(request);
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
