package com.americadigital.libupapi.WsPojos.Response.Promo;

import com.americadigital.libupapi.Pojos.Promo.PromoGetAll;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetAllPromosResponse {
    public HeaderGeneric header;
    public List<PromoGetAll> data;
    public Integer totalPages;
    public Long totalElements;

    public GetAllPromosResponse(HeaderGeneric header, List<PromoGetAll> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
