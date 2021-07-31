package com.americadigital.libupapi.WsPojos.Response.User;

import com.americadigital.libupapi.Pojos.User.UserGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetUserByIdResponse {
    public HeaderGeneric header;
    public UserGet data;

    public GetUserByIdResponse(HeaderGeneric header, UserGet data) {
        this.header = header;
        this.data = data;
    }
}
