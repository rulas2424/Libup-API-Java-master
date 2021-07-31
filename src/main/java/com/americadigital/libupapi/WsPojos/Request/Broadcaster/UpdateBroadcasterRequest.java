package com.americadigital.libupapi.WsPojos.Request.Broadcaster;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdateBroadcasterRequest {
    public String idBroadcaster;
    public String name;
    public Optional<String> pathImage;
}
