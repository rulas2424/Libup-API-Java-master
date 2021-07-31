package com.americadigital.libupapi.Pojos.Messages;

import com.americadigital.libupapi.Dao.Entity.MessagesEntity;

public class MessagesResponse {
    public String idMessage;
    public String message;
    public String dateHour;
    public MessagesEntity.TypeUser typeUser;
    public MessagesEntity.MsgType msgType;
    public String pathFile;
    public String idAdmin;
    public String nameAdmin;
    public String idUser;
    public String nameUser;
    public String idChannel;

    public MessagesResponse(String idMessage, String message, String dateHour, MessagesEntity.TypeUser typeUser, MessagesEntity.MsgType msgType, String pathFile, String idAdmin, String nameAdmin, String idUser, String nameUser, String idChannel) {
        this.idMessage = idMessage;
        this.message = message;
        this.dateHour = dateHour;
        this.typeUser = typeUser;
        this.msgType = msgType;
        this.pathFile = pathFile;
        this.idAdmin = idAdmin;
        this.nameAdmin = nameAdmin;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.idChannel = idChannel;
    }
}
