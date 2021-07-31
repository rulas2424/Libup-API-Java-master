package com.americadigital.libupapi.WsPojos.Request.Winners;

import lombok.Data;

@Data
public class WinnersShopRequest {
    public String idShop;
    public Integer page;
    public Integer maxResults;
}
