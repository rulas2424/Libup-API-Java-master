package com.americadigital.libupapi.WsPojos.Response.RBroadcasterShop;

import com.americadigital.libupapi.Pojos.RBroadcasterShop.GetRelationsBroadcasterShop;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetAllRBroadcasterShopResponse {
    public HeaderGeneric header;
    public List<GetRelationsBroadcasterShop> data;

    public GetAllRBroadcasterShopResponse(HeaderGeneric header, List<GetRelationsBroadcasterShop> data) {
        this.header = header;
        this.data = data;
    }
}
