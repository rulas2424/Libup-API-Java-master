package com.americadigital.libupapi.WsPojos.Response.Shop;

import com.americadigital.libupapi.Dao.Entity.ShopEntity;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class CommerceResponse {
    public HeaderGeneric header;
    public ShopEntity data;

    public CommerceResponse(HeaderGeneric header, ShopEntity data) {
        this.header = header;
        this.data = data;
    }
}
