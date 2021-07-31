package com.americadigital.libupapi.WsPojos.Request.User;

import lombok.Data;

import java.util.Optional;

@Data
public class UserUpdateRequest {
    public String idUser;
    public String name;
    public String lastName;
    public String email;
    public Optional<String> phoneNumber;
    public Optional<String> profilePicture;
}
