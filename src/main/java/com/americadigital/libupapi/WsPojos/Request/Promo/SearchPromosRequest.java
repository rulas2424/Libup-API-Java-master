package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import lombok.Data;

@Data
public class SearchPromosRequest {
    public String searchText;
    public PromoEntity.PromoType promoType;
    public String latitude;
    public String longitude;
    public Double range;
    public Long idState;
    public boolean activeLocation;
}
