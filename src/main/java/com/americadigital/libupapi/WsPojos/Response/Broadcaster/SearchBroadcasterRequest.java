package com.americadigital.libupapi.WsPojos.Response.Broadcaster;

import lombok.Data;

@Data
public class SearchBroadcasterRequest {
    public String searchText;
    public Integer page;
}
