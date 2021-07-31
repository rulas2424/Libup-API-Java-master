package com.americadigital.libupapi.WsPojos.Response.Hours;

import com.americadigital.libupapi.Utils.HeaderGeneric;

public class HoursResponse {
    public HeaderGeneric header;
    public String hour;

    public HoursResponse(HeaderGeneric header, String hour) {
        this.header = header;
        this.hour = hour;
    }
}
