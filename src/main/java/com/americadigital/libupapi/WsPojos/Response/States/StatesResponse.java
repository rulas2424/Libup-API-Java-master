package com.americadigital.libupapi.WsPojos.Response.States;

import com.americadigital.libupapi.Pojos.States;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class StatesResponse {
    public HeaderGeneric header;
    public List<States> data;

    public StatesResponse(HeaderGeneric header, List<States> data) {
        this.header = header;
        this.data = data;
    }
}
