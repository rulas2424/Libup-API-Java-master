package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.ChannelsBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Channel.AddChannelRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.ChannelStatusRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.GetChannelsRequest;
import com.americadigital.libupapi.WsPojos.Request.Channel.UpdateChannelRequest;
import com.americadigital.libupapi.WsPojos.Response.Channels.ChannelListResponse;
import com.americadigital.libupapi.WsPojos.Response.Channels.GetChannelResponse;
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
@RequestMapping("/api/v1/channels")
public class ChannelsController {
    private static final Logger LOG = LoggerFactory.getLogger(ChannelsController.class);
    @Autowired
    private ChannelsBusiness channelsBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addChannelRequest(@Valid @RequestBody AddChannelRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /channels/add Request: " + request + " at " + new Date());
            return channelsBusiness.createChannel(request);
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
    public ResponseEntity<HeaderResponse> updateChannelRequest(@Valid @RequestBody UpdateChannelRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /channels/update Request: " + request + " at " + new Date());
            return channelsBusiness.updateChannel(request);
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
    public ResponseEntity<HeaderResponse> changeStatus(@Valid @RequestBody ChannelStatusRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /channels/status Request: " + request + " at " + new Date());
            return channelsBusiness.changeStatus(request);
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
    public ResponseEntity<GetChannelResponse> getChannelById(@RequestParam(name = "idChannel") String idChannel) {
        GetChannelResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /channels/getById Request: " + idChannel + " at " + new Date());
            return channelsBusiness.getChannelById(idChannel);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetChannelResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetChannelResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ChannelListResponse> getAllChannelsForPanel(@Valid @RequestBody GetChannelsRequest request) {
        ChannelListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /channels/getAll Request: " + request + " at " + new Date());
            return channelsBusiness.getChannelsForPanel(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findAllActives",
            method = RequestMethod.GET)
    public ResponseEntity<ChannelListResponse> getAllChannelsActives() {
        ChannelListResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /channels/findAll Request: " + " at " + new Date());
            return channelsBusiness.getChannelsActives();
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ChannelListResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> deleteChannel(@RequestParam(name = "idChannel") String idChannel) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry DELETE Method: /channels/delete Request: " + idChannel + " at " + new Date());
            return channelsBusiness.deleteChannel(idChannel);
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
