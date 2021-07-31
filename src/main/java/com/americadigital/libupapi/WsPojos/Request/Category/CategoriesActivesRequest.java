package com.americadigital.libupapi.WsPojos.Request.Category;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import lombok.Data;

@Data
public class CategoriesActivesRequest {
    public PromoEntity.PromoType promoType;
    public String latitude;
    public String longitude;
    public Double range;
    public boolean activeLocation;
    public Long idState;
}
