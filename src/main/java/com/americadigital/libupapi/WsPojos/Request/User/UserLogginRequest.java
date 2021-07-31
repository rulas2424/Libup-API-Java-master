package com.americadigital.libupapi.WsPojos.Request.User;

import com.americadigital.libupapi.Dao.Entity.UserEntity;
import lombok.Data;

@Data
public class UserLogginRequest {
    public String email;
    public String password;
    public String tokenDevice;
    public UserEntity.OperativeSystem operativeSystem;

}

