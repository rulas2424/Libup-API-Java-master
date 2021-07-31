package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.MessagesBusiness;
import com.americadigital.libupapi.Dao.Entity.MessagesEntity;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Messages.SendMessagesRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.GetMessagesResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.SendMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/messages")
public class MessagesController {
    private static final Logger LOG = LoggerFactory.getLogger(MessagesController.class);

    private final SimpMessagingTemplate template;

    @Autowired
    public MessagesController(final SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    private MessagesBusiness messagesBusiness;

    @MessageMapping("/hello")
    @PostMapping(value = "/helloTest")
    @ResponseBody
    public ResponseEntity<HeaderResponse> greeting(String name) {
        ResponseEntity<HeaderResponse> test = messagesBusiness.test(this.template);
        return test;
    }

    @MessageMapping("/send/message")
    @PostMapping(value = "/api/sendMessages")
    @ResponseBody
    public ResponseEntity<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessagesRequest request) {
        SendMessageResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /messages/send Request: " + " at " + new Date());
            return messagesBusiness.sendMessages(request, this.template);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/uploadFiles",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = {"multipart/form-data"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SendMessageResponse> uploadFiles(@RequestParam(name = "typeUser") MessagesEntity.TypeUser typeUser,
                                                           @RequestParam(name = "msgType") MessagesEntity.MsgType msgType,
                                                           @RequestParam(name = "pathFile") MultipartFile pathFile, @RequestParam(name = "idChannel") String idChannel,
                                                           @RequestParam(name = "idRadio", required = false) Optional<String> idRadio, @RequestParam(name = "idUser", required = false) Optional<String> idUser) {
        SendMessageResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /messages/send Request: " + " at " + new Date());
            return messagesBusiness.uploadFiles(typeUser, msgType, pathFile, idChannel, idRadio, idUser, this.template);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getMessages",
            method = RequestMethod.GET)
    public ResponseEntity<GetMessagesResponse> getMessages(@RequestParam(name = "idChannel") String idChannel) {
        GetMessagesResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry GET Method: /messages/getMessages Request: " + idChannel + " at " + new Date());
            return messagesBusiness.getMessagesByIdChannel(idChannel);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetMessagesResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetMessagesResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
