package com.americadigital.libupapi.WsPojos.Request.Channel;

import lombok.Data;

@Data
public class GetChannelsRequest {
    public String idBroadcaster;
    public Integer page;
    public Integer maxResults;
}
