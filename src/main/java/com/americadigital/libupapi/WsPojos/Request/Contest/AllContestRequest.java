package com.americadigital.libupapi.WsPojos.Request.Contest;

import lombok.Data;

@Data
public class AllContestRequest {
    public String idBroadcaster;
    public boolean containAudio;
    public Integer page;
    public Integer maxResults;
}
