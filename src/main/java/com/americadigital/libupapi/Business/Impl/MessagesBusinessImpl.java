package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.MessagesBusiness;
import com.americadigital.libupapi.Dao.Entity.MessagesEntity;
import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import com.americadigital.libupapi.Dao.Entity.UserEntity;
import com.americadigital.libupapi.Dao.Interfaces.Admin.UserAdminDao;
import com.americadigital.libupapi.Dao.Interfaces.Channels.ChannelsDao;
import com.americadigital.libupapi.Dao.Interfaces.Messages.MessagesDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Messages.MessageGet;
import com.americadigital.libupapi.Pojos.Messages.MessagesResponse;
import com.americadigital.libupapi.Utils.*;
import com.americadigital.libupapi.WsPojos.Request.Messages.SendMessagesRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.GetMessagesResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.SendMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MessagesBusinessImpl implements MessagesBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(MessagesBusinessImpl.class);
    private static final String IMAGE_FOLDER = ConstantsTexts.TOMCAT_HOME_PATH + "/" + ConstantsTexts.NAME_FOLDER_ADJUNTOS + "/";

    @Autowired
    private UserAdminDao userAdminDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private ChannelsDao channelsDao;

    @Autowired
    private MessagesDao messagesDao;

    @Override
    public ResponseEntity<SendMessageResponse> sendMessages(SendMessagesRequest request, SimpMessagingTemplate simpMessagingTemplate) {
        String msg;
        SendMessageResponse response;
        UserAdminEntity userAdminEntity = null;
        UserEntity userEntity = null;
        channelsDao.findById(request.idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        String radio = !request.idRadio.isPresent() ? null : request.idRadio.orElse(null);
        String user = !request.idUser.isPresent() ? null : request.idUser.orElse(null);
        MessagesEntity messagesEntity = new MessagesEntity();
        if (radio != null) {
            userAdminEntity = userAdminDao.findByIdAdmin(request.idRadio.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID + ": RADIO"));
            messagesEntity.idAdmin = request.idRadio.get();
        }
        if (user != null) {
            userEntity = userEntityDao.findById(request.idUser.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID + "DE LA APP."));
            messagesEntity.idUser = request.idUser.get();
        }
        messagesEntity.active = true;
        messagesEntity.dateHour = new Date();
        messagesEntity.idChannel = request.idChannel;
        messagesEntity.message = request.message;
        String idMessage = new GenerateUuid().generateUuid();
        if (messagesDao.existsById(idMessage)) {
            throw new ConflictException(ConstantsTexts.ERROR_MESSAGE);
        }
        messagesEntity.isDeleted = false;
        messagesEntity.msgType = request.msgType;
        messagesEntity.typeUser = request.typeUser;
        messagesEntity.idMessage = idMessage;
        msg = ConstantsTexts.MSG_SEND;
        MessagesEntity save = messagesDao.save(messagesEntity);
        response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new MessagesResponse(save.idMessage, save.message, new ConvertToDate().convertirFecha(ConstantsTexts.fechaMsg.format(save.dateHour)), save.typeUser, save.msgType, save.pathFile, userAdminEntity == null ? null : request.idRadio.get(), userAdminEntity == null ? null : userAdminEntity.name + " " + userAdminEntity.lastName, userEntity == null ? null : request.idUser.get(), userEntity == null ? null : userEntity.name + " " + userEntity.lastName, request.idChannel));
        simpMessagingTemplate.convertAndSend("/chat/" + request.idChannel, new MessageGet(new MessagesResponse(save.idMessage, save.message, new ConvertToDate().convertirFecha(ConstantsTexts.fechaMsg.format(save.dateHour)), save.typeUser, save.msgType, save.pathFile, userAdminEntity == null ? null : request.idRadio.get(), userAdminEntity == null ? null : userAdminEntity.name + " " + userAdminEntity.lastName, userEntity == null ? null : request.idUser.get(), userEntity == null ? null : userEntity.name + " " + userEntity.lastName, request.idChannel)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SendMessageResponse> uploadFiles(MessagesEntity.TypeUser typeUser, MessagesEntity.MsgType msgType, MultipartFile pathFile, String idChannel, Optional<String> idRadio, Optional<String> idUser, SimpMessagingTemplate simpMessagingTemplate) {
        String msg;
        SendMessageResponse response;
        UserAdminEntity userAdminEntity = null;
        UserEntity userEntity = null;
        channelsDao.findById(idChannel).orElseThrow(() -> new ConflictException(ConstantsTexts.CHANNEL_INVALID));
        String radio = !idRadio.isPresent() ? null : idRadio.orElse(null);
        String user = !idUser.isPresent() ? null : idUser.orElse(null);
        MessagesEntity messagesEntity = new MessagesEntity();
        if (radio != null) {
            userAdminEntity = userAdminDao.findByIdAdmin(idRadio.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID + ": RADIO"));
            messagesEntity.idAdmin = idRadio.get();
        }
        if (user != null) {
            userEntity = userEntityDao.findById(idUser.get()).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID + "DE LA APP."));
            messagesEntity.idUser = idUser.get();
        }
        messagesEntity.active = true;
        messagesEntity.dateHour = new Date();
        messagesEntity.idChannel = idChannel;
        String idMessage = new GenerateUuid().generateUuid();
        if (messagesDao.existsById(idMessage)) {
            throw new ConflictException(ConstantsTexts.ERROR_MESSAGE);
        }
        messagesEntity.isDeleted = false;
        messagesEntity.msgType = msgType;
        messagesEntity.typeUser = typeUser;
        messagesEntity.idMessage = idMessage;
        String path = saveFile(pathFile);
        messagesEntity.pathFile = path;
        msg = ConstantsTexts.MSG_SEND;
        MessagesEntity save = messagesDao.save(messagesEntity);
        response = new SendMessageResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new MessagesResponse(save.idMessage, save.message, new ConvertToDate().convertirFecha(ConstantsTexts.fechaMsg.format(save.dateHour)), save.typeUser, save.msgType, save.pathFile, userAdminEntity == null ? null : idRadio.get(), userAdminEntity == null ? null : userAdminEntity.name + " " + userAdminEntity.lastName, userEntity == null ? null : idUser.get(), userEntity == null ? null : userEntity.name + " " + userEntity.lastName, idChannel));
        simpMessagingTemplate.convertAndSend("/chat/" + idChannel, new MessageGet(new MessagesResponse(save.idMessage, save.message, new ConvertToDate().convertirFecha(ConstantsTexts.fechaMsg.format(save.dateHour)), save.typeUser, save.msgType, save.pathFile, userAdminEntity == null ? null : idRadio.get(), userAdminEntity == null ? null : userAdminEntity.name + " " + userAdminEntity.lastName, userEntity == null ? null : idUser.get(), userEntity == null ? null : userEntity.name + " " + userEntity.lastName, idChannel)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String saveFile(MultipartFile file) {
        SaveFiles saveFiles = new SaveFiles();
        String pathFile = saveFiles.fileUpload(file, IMAGE_FOLDER);
        return pathFile;
    }

    @Override
    public ResponseEntity<GetMessagesResponse> getMessagesByIdChannel(String idChannel) {
        String message;
        GetMessagesResponse response;
        List<MessagesResponse> messagesResponses = new ArrayList<>();
        List<MessagesEntity> allMessagesByIdChannel = messagesDao.findAllMessagesByIdChannel(idChannel);
        if (allMessagesByIdChannel.isEmpty()) {
            message = ConstantsTexts.EMPTY_LIST;
            LOG.info(message);
            response = new GetMessagesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), message), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        allMessagesByIdChannel.forEach(msg -> messagesResponses.add(new MessagesResponse(msg.idMessage, msg.message, new ConvertToDate().convertirFecha(ConstantsTexts.fechaMsg.format(msg.dateHour)), msg.typeUser,
                msg.msgType, msg.pathFile, msg.idAdmin, msg.idAdmin == null ? null : msg.userAdminEntity.name + " " + msg.userAdminEntity.lastName, msg.idUser, msg.idUser == null ? null : msg.userEntity.name + " " + msg.userEntity.lastName, idChannel)));
        message = ConstantsTexts.MSG_LIST;
        response = new GetMessagesResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), message), messagesResponses);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HeaderResponse> test(SimpMessagingTemplate simpMessagingTemplate) {
        simpMessagingTemplate.convertAndSend("/chat/f7a40a6f843411ea9", "Mensage desde el backend Spring boot...");
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
