package com.americadigital.libupapi.WsPojos.Request.User;

import lombok.Data;


@Data
public class ChangeStatusUserRequest {
    public String idUser;
    public Boolean active;
}
