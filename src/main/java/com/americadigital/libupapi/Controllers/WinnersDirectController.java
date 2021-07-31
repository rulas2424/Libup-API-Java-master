package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.WinnersDirectBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersShopRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.WinnersDirect.WinnerListDirectResponse;
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
@RequestMapping("/api/v1/winnersDirect")
public class WinnersDirectController {
    private static final Logger LOG = LoggerFactory.getLogger(WinnersDirectController.class);

    @Autowired
    private WinnersDirectBusiness winnersDirectBusiness;


    @RequestMapping(value = "/getById",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WinnerListDirectResponse> searchAwardsWinningDirect(@RequestParam(value = "idUser") String idUser) {
        String msg;
        WinnerListDirectResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/winners/getById Request: " + idUser + " at " + new Date());
            return winnersDirectBusiness.findHistoryWinnerDirectByUserId(idUser);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/shop/findById",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WinnerListDirectResponse> getAllWinnersDirectByShop(@Valid @RequestBody WinnersShopRequest winnersShopRequest) {
        String msg;
        WinnerListDirectResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/winners/shop/findById Request: " + winnersShopRequest + " at " + new Date());
            return winnersDirectBusiness.getAllWinnersDirectByIdShop(winnersShopRequest);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/award/reclaim",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> reclaimAward(@Valid @RequestParam(value = "idWinner") String idWinner) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /v1/winners//award/reclaim Request: " + idWinner + " at " + new Date());
            return winnersDirectBusiness.claimAwardDirect(idWinner);
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
