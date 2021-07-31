package com.americadigital.libupapi.WsPojos.Response.Contest;

import com.americadigital.libupapi.Utils.HeaderGeneric;

public class ContestGetResponse {
    public HeaderGeneric header;
    public ContestResponse data;

    public ContestGetResponse(HeaderGeneric header, ContestResponse data) {
        this.header = header;
        this.data = data;
    }
}
