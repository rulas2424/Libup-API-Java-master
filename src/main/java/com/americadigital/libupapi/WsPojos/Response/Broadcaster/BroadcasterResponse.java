package com.americadigital.libupapi.WsPojos.Response.Broadcaster;

import com.americadigital.libupapi.Pojos.Broadcaster.Broadcaster;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class BroadcasterResponse {
    public HeaderGeneric header;
    public Broadcaster data;

    public BroadcasterResponse(HeaderGeneric header, Broadcaster data) {
        this.header = header;
        this.data = data;
    }
}
