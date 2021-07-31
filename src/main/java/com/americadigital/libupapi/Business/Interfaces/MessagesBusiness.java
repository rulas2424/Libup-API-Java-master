package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.Dao.Entity.MessagesEntity;
import com.americadigital.libupapi.WsPojos.Request.Messages.SendMessagesRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.GetMessagesResponse;
import com.americadigital.libupapi.WsPojos.Response.Messages.SendMessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface MessagesBusiness {
    ResponseEntity<SendMessageResponse> sendMessages(SendMessagesRequest request, SimpMessagingTemplate simpMessagingTemplate);

    ResponseEntity<SendMessageResponse> uploadFiles(MessagesEntity.TypeUser typeUser, MessagesEntity.MsgType msgType, MultipartFile pathFile,
                                                    String idChannel, Optional<String> idRadio, Optional<String> idUser, SimpMessagingTemplate simpMessagingTemplate);

    ResponseEntity<GetMessagesResponse> getMessagesByIdChannel(String idChannel);

    ResponseEntity<HeaderResponse> test(SimpMessagingTemplate simpMessagingTemplate);
}

