package com.americadigital.libupapi.Pojos.UserAdmin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;

public class LoginAdmin {
    public String idUser;
    public String name;
    public String lastName;
    public UserAdminEntity.TypeAdmin typeAdmin;
    public String jwt;
    public String idShop;
    public String idBroadcaster;
    public String nameShop;
    public String latitude;
    public String longitude;
    public boolean isPlanActive;
    public int notificationsAllowed;
    public int notificationsUsed;
    public String dateEnded;

    public LoginAdmin(String idUser, String name, String lastName, UserAdminEntity.TypeAdmin typeAdmin, String jwt, String idShop, String idBroadcaster, String nameShop, String latitude, String longitude, boolean isPlanActive, int notificationsAllowed, int notificationsUsed, String dateEnded) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.typeAdmin = typeAdmin;
        this.jwt = jwt;
        this.idShop = idShop;
        this.idBroadcaster = idBroadcaster;
        this.nameShop = nameShop;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isPlanActive = isPlanActive;
        this.notificationsAllowed = notificationsAllowed;
        this.notificationsUsed = notificationsUsed;
        this.dateEnded = dateEnded;
    }
}
