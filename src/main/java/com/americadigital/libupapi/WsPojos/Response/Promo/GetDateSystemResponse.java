package com.americadigital.libupapi.WsPojos.Response.Promo;

import com.americadigital.libupapi.Pojos.Promo.DateSystem;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetDateSystemResponse {
    public HeaderGeneric header;
    public DateSystem data;

    public GetDateSystemResponse(HeaderGeneric header, DateSystem data) {
        this.header = header;
        this.data = data;
    }
}
