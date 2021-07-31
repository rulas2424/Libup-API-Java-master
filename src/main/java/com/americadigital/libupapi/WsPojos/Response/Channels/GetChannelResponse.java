package com.americadigital.libupapi.WsPojos.Response.Channels;

import com.americadigital.libupapi.Pojos.Channels.ChannelsGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetChannelResponse {
    public HeaderGeneric header;
    public ChannelsGet data;

    public GetChannelResponse(HeaderGeneric header, ChannelsGet data) {
        this.header = header;
        this.data = data;
    }
}
