package com.americadigital.libupapi.WsPojos.Response.Promo;

import com.americadigital.libupapi.Pojos.Promo.PromoGetAll;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetPromoResponse {
    public HeaderGeneric header;
    public PromoGetAll data;

    public GetPromoResponse(HeaderGeneric header, PromoGetAll data) {
        this.header = header;
        this.data = data;
    }
}
