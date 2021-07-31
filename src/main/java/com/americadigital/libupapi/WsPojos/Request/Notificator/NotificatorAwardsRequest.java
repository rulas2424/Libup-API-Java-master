package com.americadigital.libupapi.WsPojos.Request.Notificator;

import com.americadigital.libupapi.Dao.Entity.ShopNotificationsEntity;
import lombok.Data;

@Data
public class NotificatorAwardsRequest {
    public String idPromo;
    public String latitude;
    public String longitude;
    public String idShop;
    public Double rangeKilometers;
    public boolean allUsers;
    public boolean sendNotification;
    public ShopNotificationsEntity.TypeNotify typeNotify;
}
