package com.americadigital.libupapi.WsPojos.Request.Messages;

import com.americadigital.libupapi.Dao.Entity.MessagesEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Data
public class SendMessagesRequest {
    public String message;
    public MessagesEntity.TypeUser typeUser;
    public  MessagesEntity.MsgType msgType;
    public String idChannel;
    public Optional<String> idRadio;
    public Optional<String> idUser;

    public SendMessagesRequest(String message, MessagesEntity.TypeUser typeUser, MessagesEntity.MsgType msgType, String idChannel, Optional<String> idRadio, Optional<String> idUser) {
        this.message = message;
        this.typeUser = typeUser;
        this.msgType = msgType;
        this.idChannel = idChannel;
        this.idRadio = idRadio;
        this.idUser = idUser;
    }
}
