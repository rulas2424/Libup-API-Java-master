package com.americadigital.libupapi.WsPojos.Request.ContestDetail;

import lombok.Data;

@Data
public class ContestDetailRequest {
    public String idContest;
    public String idUser;
    public int tickCount;
}
