package com.americadigital.libupapi.WsPojos.Request.UserAdmin;

import lombok.Data;

@Data
public class ChangePassRequest {
    public String idAdmin;
    public String newPassword;
}
