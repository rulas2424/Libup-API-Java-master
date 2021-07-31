package com.americadigital.libupapi.Pojos.Broadcaster;

public class Broadcaster {
    public String idBroadcaster;
    public String name;
    public boolean active;
    public boolean isDeleted;
    public String imagePath;

    public Broadcaster(String idBroadcaster, String name, boolean active, boolean isDeleted, String imagePath) {
        this.idBroadcaster = idBroadcaster;
        this.name = name;
        this.active = active;
        this.isDeleted = isDeleted;
        this.imagePath = imagePath;
    }
}
