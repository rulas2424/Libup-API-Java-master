package com.americadigital.libupapi.WsPojos.Response.User;

import com.americadigital.libupapi.Pojos.User.Register;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class UserRegisterResponse {
    public HeaderGeneric header;
    public Register data;

    public UserRegisterResponse(HeaderGeneric header, Register data) {
        this.header = header;
        this.data = data;
    }
}
