package com.americadigital.libupapi.WsPojos.Response.Broadcaster;

import com.americadigital.libupapi.Pojos.Broadcaster.Broadcaster;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class BroadcasterListResponse {
    public HeaderGeneric header;
    public List<Broadcaster> data;
    public Integer totalPages;
    public Long totalElements;

    public BroadcasterListResponse(HeaderGeneric header, List<Broadcaster> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
