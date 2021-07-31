package com.americadigital.libupapi.Pojos.Channels;

public class ChannelsGet {
    public String idChannel;
    public String tittle;
    public String description;
    public String url;
    public boolean active;
    public boolean isDeleted;
    public String pathImage;
    public String idBroadcaster;
    public String nameBroadcaster;

    public ChannelsGet(String idChannel, String tittle, String description, String url, boolean active, boolean isDeleted, String pathImage, String idBroadcaster, String nameBroadcaster) {
        this.idChannel = idChannel;
        this.tittle = tittle;
        this.description = description;
        this.url = url;
        this.active = active;
        this.isDeleted = isDeleted;
        this.pathImage = pathImage;
        this.idBroadcaster = idBroadcaster;
        this.nameBroadcaster = nameBroadcaster;
    }
}
