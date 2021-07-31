package com.americadigital.libupapi.WsPojos.Response.Promo;

import com.americadigital.libupapi.Pojos.Promo.PromosAllCategory;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetPromosAppResponse {
    public HeaderGeneric header;
    public List<PromosAllCategory> data;

    public GetPromosAppResponse(HeaderGeneric header, List<PromosAllCategory> data) {
        this.header = header;
        this.data = data;
    }
}
