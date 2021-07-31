package com.americadigital.libupapi.WsPojos.Response.Channels;

import com.americadigital.libupapi.Pojos.Channels.ChannelsGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class ChannelListResponse {
    public HeaderGeneric header;
    public List<ChannelsGet> data;
    public Integer totalPages;
    public Long totalElements;

    public ChannelListResponse(HeaderGeneric header, List<ChannelsGet> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public ChannelListResponse(HeaderGeneric header, List<ChannelsGet> data) {
        this.header = header;
        this.data = data;
    }
}
