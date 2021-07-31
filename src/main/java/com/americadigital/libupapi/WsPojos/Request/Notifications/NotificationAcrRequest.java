package com.americadigital.libupapi.WsPojos.Request.Notifications;

import com.americadigital.libupapi.Dao.Entity.ShopNotificationsEntity;
import lombok.Data;

@Data
public class NotificationAcrRequest {
    public String acrId;
    public String idContest;
    public int secondsDuration;
    public boolean withAudio;
    public String latitude;
    public String longitude;
    public Double rangeKilometers;
    public boolean allUsers;
    public ShopNotificationsEntity.TypeNotify typeNotify;
}
