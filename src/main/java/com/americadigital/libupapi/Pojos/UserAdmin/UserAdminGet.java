package com.americadigital.libupapi.Pojos.UserAdmin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;

public class UserAdminGet {
    public String idAdmin;
    public String name;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Boolean active;
    public UserAdminEntity.TypeAdmin typeAdmin;
    public String profile;
    public String idShop;
    public String nameShop;
    public String idBroadcaster;
    public String nameBroadcaster;

    public UserAdminGet(String idAdmin, String name, String lastName, String email, String phoneNumber, Boolean active, UserAdminEntity.TypeAdmin typeAdmin, String profile, String idShop, String nameShop, String idBroadcaster, String nameBroadcaster) {
        this.idAdmin = idAdmin;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.typeAdmin = typeAdmin;
        this.profile = profile;
        this.idShop = idShop;
        this.nameShop = nameShop;
        this.idBroadcaster = idBroadcaster;
        this.nameBroadcaster = nameBroadcaster;
    }
}
