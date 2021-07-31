package com.americadigital.libupapi.WsPojos.Request.User;

import lombok.Data;

@Data
public class UserCoordinatesRequest {
    public String idUser;
    public String latitude;
    public String longitude;
}
