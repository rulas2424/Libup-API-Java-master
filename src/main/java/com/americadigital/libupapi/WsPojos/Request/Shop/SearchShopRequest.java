package com.americadigital.libupapi.WsPojos.Request.Shop;

import lombok.Data;

@Data
public class SearchShopRequest {
    public String searchText;
    public Integer page;
}
