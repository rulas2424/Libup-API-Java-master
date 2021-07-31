package com.americadigital.libupapi.WsPojos.Request.Channel;

import lombok.Data;

import java.util.Optional;

@Data
public class AddChannelRequest {
    public String tittle;
    public Optional<String> description;
    public Optional<String> url;
    public String image;
    public String idBroadcaster;
}
