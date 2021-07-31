package com.americadigital.libupapi.WsPojos.Response.User;

import com.americadigital.libupapi.Pojos.User.Login;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class UserLoginResponse {
    public HeaderGeneric header;
    public Login data;

    public UserLoginResponse(HeaderGeneric header, Login data) {
        this.header = header;
        this.data = data;
    }
}
