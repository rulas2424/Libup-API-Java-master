package com.americadigital.libupapi.WsPojos.Response.UserAdmin;

import com.americadigital.libupapi.Pojos.UserAdmin.UserAdminGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetUserAdminByIdResponse {
    public HeaderGeneric header;
    public UserAdminGet data;

    public GetUserAdminByIdResponse(HeaderGeneric header, UserAdminGet data) {
        this.header = header;
        this.data = data;
    }
}
