package com.americadigital.libupapi.WsPojos.Request.UserAdmin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class UserAdminUpdateRequest {
    public String idAdmin;
    public String name;
    public String lastName;
    public String email;
    public Optional<String> phoneNumber;
    public Optional<String> profilePicture;
    public UserAdminEntity.TypeAdmin typeAdmin;
    public Optional<String> idShop;
    public Optional<String> idBroadcaster;
}
