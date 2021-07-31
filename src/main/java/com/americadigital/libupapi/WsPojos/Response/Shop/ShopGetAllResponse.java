package com.americadigital.libupapi.WsPojos.Response.Shop;

import com.americadigital.libupapi.Pojos.Shop.GetAllShops;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class ShopGetAllResponse {
    public HeaderGeneric header;
    public GetAllShops data;
    public Integer totalPages;
    public Long totalElements;

    public ShopGetAllResponse(HeaderGeneric header, GetAllShops data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
