package com.americadigital.libupapi.WsPojos.Request.Channel;

import lombok.Data;

import java.util.Optional;

@Data
public class UpdateChannelRequest {
    public String idChannel;
    public String tittle;
    public Optional<String> description;
    public Optional<String> url;
    public Optional<String> image;
}
