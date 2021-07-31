package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.AudiosAdnContestBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Contest.AddContestNotAudio;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Contest.AllContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ChangeStatusContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ContestShopRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.TerminateContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Notifications.NotificationAcrRequest;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestGetResponse;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestListResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/acrCloud/audios")
public class AudiosAndContestController {
    private static final Logger LOG = LoggerFactory.getLogger(AudiosAndContestController.class);

    @Autowired
    private AudiosAdnContestBusiness audiosAdnContestBusiness;

    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> uploadAndAddContest(@RequestParam(value = "audioTitle") String audioTitle, @RequestParam(value = "dataType") String dataType, @RequestParam(value = "bucketName") String bucketName, @RequestParam(value = "file") MultipartFile file, @RequestParam(value = "idAdmin") String idAdmin, @RequestParam(value = "idPromo") String idPromo, @RequestParam(value = "idShop") String idShop, @RequestParam(value = "idBroadcaster") String idBroadcaster) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /upload Request: " + audioTitle + " at " + new Date());
            return audiosAdnContestBusiness.uploadAudioAndAddContest(audioTitle, dataType, bucketName, file, idAdmin, idPromo, idShop, idBroadcaster);
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

    @RequestMapping(value = "/createContest",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> createContestNotAudio(@Valid @RequestBody AddContestNotAudio request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /createContest Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.createContestNotAudio(request);
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

    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> updateUploadAndAddContest(@RequestParam(value = "audioTitle") String audioTitle, @RequestParam(value = "file", required = false) Optional<MultipartFile> file, @RequestParam(value = "idContest") String idContest) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /UPDATE Request: " + idContest + " at " + new Date());
            return audiosAdnContestBusiness.updateAudioAndContest(audioTitle, file, idContest);
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


    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changeStatusContest(@Valid @RequestBody ChangeStatusContestRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /branch/status Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.updateStatusContest(request);
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


    @RequestMapping(value = "/getByIdAcr",
            method = RequestMethod.GET)
    public ResponseEntity<ContestGetResponse> getContestByIdAcr(@RequestParam(value = "idAcr") String idAcr) {
        ContestGetResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /branch/getByIdAcr Request: " + idAcr + " at " + new Date());
            return audiosAdnContestBusiness.getContestByIdAcr(idAcr);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ContestGetResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ContestGetResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getList",
            method = RequestMethod.POST)
    public ResponseEntity<ContestListResponse> getListContest(@Valid @RequestBody AllContestRequest request) {
        ContestListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /branch/getList Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.getListContestByIdBroadcaster(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getListByShop",
            method = RequestMethod.POST)
    public ResponseEntity<ContestListResponse> getListContestByShop(@Valid @RequestBody ContestShopRequest request) {
        ContestListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /getListByShop Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.getListContestByIdShop(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ContestListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/notifications",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> sendNotifications(@Valid @RequestBody NotificationAcrRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /notifications Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.sendNotifications(request);
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

    @RequestMapping(value = "/contest/terminate",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> terminateContest(@Valid @RequestBody TerminateContestRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /contest/terminate Request: " + request + " at " + new Date());
            return audiosAdnContestBusiness.terminateContest(request);
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
