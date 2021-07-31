package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import lombok.Data;

@Data
public class AllPromosRequest {
    public String idShop;
    public PromoEntity.PromoType promoType;
    public Integer page;
    public Integer maxResults;
}
