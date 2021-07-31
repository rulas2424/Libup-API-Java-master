package com.americadigital.libupapi.WsPojos.Request.Promo;

import lombok.Data;

@Data
public class AllAwardsRequest {
    public String idShop;
    public Integer page;
    public Integer maxResults;
}
