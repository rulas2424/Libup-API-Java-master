package com.americadigital.libupapi.WsPojos.Response.Messages;

import com.americadigital.libupapi.Pojos.Messages.MessagesResponse;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class SendMessageResponse {
    public HeaderGeneric header;
    public MessagesResponse data;

    public SendMessageResponse(HeaderGeneric header, MessagesResponse data) {
        this.header = header;
        this.data = data;
    }
}
