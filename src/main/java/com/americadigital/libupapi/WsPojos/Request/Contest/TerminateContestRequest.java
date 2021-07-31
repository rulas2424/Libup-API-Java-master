package com.americadigital.libupapi.WsPojos.Request.Contest;

import com.americadigital.libupapi.Dao.Entity.ShopNotificationsEntity;
import lombok.Data;

@Data
public class TerminateContestRequest {
    public String idShop;
    public String idContest;
    public boolean containAudio;
    public ShopNotificationsEntity.TypeNotify typeNotify;
}
