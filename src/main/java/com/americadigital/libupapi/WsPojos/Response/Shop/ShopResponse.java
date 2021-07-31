package com.americadigital.libupapi.WsPojos.Response.Shop;

import com.americadigital.libupapi.Pojos.Shop.ShopGetAll;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class ShopResponse {
    public HeaderGeneric header;
    public List<ShopGetAll> data;

    public ShopResponse(HeaderGeneric header, List<ShopGetAll> data) {
        this.header = header;
        this.data = data;
    }
}
