package com.americadigital.libupapi.WsPojos.Response.Shop;

import com.americadigital.libupapi.Dao.Entity.ShopEntity;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class AllShopResponse {
    public HeaderGeneric header;
    public List<ShopEntity> data;

    public AllShopResponse(HeaderGeneric header, List<ShopEntity> data) {
        this.header = header;
        this.data = data;
    }
}
