package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Response.Hours.HoursResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/hours")
public class HourController {
    private static final Logger LOG = LoggerFactory.getLogger(HourController.class);

    @RequestMapping(value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<HoursResponse> getHourSystem() {
        HoursResponse response;
        String msg;
        LOG.info("Endpoint entry GET Method: /hours/get: " + " at " + new Date());
        Date date = new Date();
        String hourActual = ConstantsTexts.hour.format(date);
        msg = ConstantsTexts.HOUR_GET;
        response = new HoursResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), hourActual);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
