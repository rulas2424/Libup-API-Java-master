package com.americadigital.libupapi.WsPojos.Response.AcrCloud;

import com.americadigital.libupapi.Utils.HeaderGeneric;

public class AcrResponse {
    public HeaderGeneric header;
    public Object data;


    public AcrResponse(HeaderGeneric header, Object data) {
        this.header = header;
        this.data = data;
    }
}
