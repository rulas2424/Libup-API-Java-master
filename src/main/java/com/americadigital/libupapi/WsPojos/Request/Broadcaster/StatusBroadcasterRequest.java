package com.americadigital.libupapi.WsPojos.Request.Broadcaster;

import lombok.Data;

@Data
public class StatusBroadcasterRequest {
    public String idBroadcaster;
    public boolean status;
}
