package com.americadigital.libupapi.WsPojos.Response.Messages;

import com.americadigital.libupapi.Pojos.Messages.MessagesResponse;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetMessagesResponse {
    public HeaderGeneric header;
    public List<MessagesResponse> data;

    public GetMessagesResponse(HeaderGeneric header, List<MessagesResponse> data) {
        this.header = header;
        this.data = data;
    }
}
