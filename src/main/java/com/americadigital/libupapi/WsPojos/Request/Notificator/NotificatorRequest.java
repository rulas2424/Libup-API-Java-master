package com.americadigital.libupapi.WsPojos.Request.Notificator;

import com.americadigital.libupapi.Dao.Entity.ShopNotificationsEntity;
import lombok.Data;

@Data
public class NotificatorRequest {
    public String idPromo;
    public String latitude;
    public String longitude;
    public String idShop;
    public String namePromo;
    public String pathImage;
    public Double rangeKilometers;
    public boolean allUsers;
    public ShopNotificationsEntity.TypeNotify typeNotify;
}
