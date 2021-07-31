package com.americadigital.libupapi.WsPojos.Request.User;

import lombok.Data;

@Data
public class SearchRequest {
    public String searchText;
    public Integer page;
}
