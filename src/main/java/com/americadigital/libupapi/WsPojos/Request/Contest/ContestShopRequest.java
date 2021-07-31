package com.americadigital.libupapi.WsPojos.Request.Contest;

import lombok.Data;

@Data
public class ContestShopRequest {
    public String idShop;
    public boolean containAudio;
    public Integer page;
    public Integer maxResults;
}
