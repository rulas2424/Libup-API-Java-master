package com.americadigital.libupapi.WsPojos.Response.User;

import com.americadigital.libupapi.Pojos.User.Token;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class RefreshTokenResponse {
    public HeaderGeneric header;
    public Token data;

    public RefreshTokenResponse(HeaderGeneric header, Token data) {
        this.header = header;
        this.data = data;
    }
}
