package com.americadigital.libupapi.WsPojos.Request.Channel;

import lombok.Data;

@Data
public class ChannelStatusRequest {
    public String idChannel;
    public boolean status;
}
