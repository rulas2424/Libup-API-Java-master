package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import lombok.Data;

@Data
public class AllPromosShopRequest {
    public String idShop;
    public PromoEntity.PromoType type;
    public Integer page;
    public Integer maxResults;
}
