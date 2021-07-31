package com.americadigital.libupapi.WsPojos.Request.User;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    public String idUser;
    public String currentPassword;
    public String newPassword;
}
