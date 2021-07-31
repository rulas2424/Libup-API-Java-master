package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.BucketsBusiness;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.AcrCloud.Buckets.AddBucketRequest;
import com.americadigital.libupapi.WsPojos.Response.AcrCloud.AcrResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/acrCloud/buckets")
public class BucketsController {
    @Autowired
    private BucketsBusiness bucketsBusiness;
    private static final Logger LOG = LoggerFactory.getLogger(BucketsController.class);

    @RequestMapping(value = "/get",
            method = RequestMethod.GET)
    public ResponseEntity<AcrResponse> getBuckets() {
        String msg;
        AcrResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /buckets/get Request: " + " at " + new Date());
            return bucketsBusiness.getAllBuckets();
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new AcrResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AcrResponse> addBucket(@Valid @RequestBody final AddBucketRequest request) {
        String msg;
        AcrResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /buckets/add Request: " + request + " at " + new Date());
            return bucketsBusiness.addBucket(request);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new AcrResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE)
    public ResponseEntity<HeaderGeneric> deleteBucket(@RequestParam(value = "name") String name) {
        String msg;
        HeaderGeneric response;
        try {
            LOG.info("Endpoint entry POST Method: /buckets/delete Request: " + name + " at " + new Date());
            return bucketsBusiness.deleteBucket(name);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
