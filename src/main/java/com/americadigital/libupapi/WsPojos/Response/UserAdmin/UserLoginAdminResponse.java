package com.americadigital.libupapi.WsPojos.Response.UserAdmin;

import com.americadigital.libupapi.Pojos.UserAdmin.LoginAdmin;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class UserLoginAdminResponse {
    public HeaderGeneric header;
    public LoginAdmin data;

    public UserLoginAdminResponse(HeaderGeneric header, LoginAdmin data) {
        this.header = header;
        this.data = data;
    }
}
