package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import lombok.Data;

@Data
public class GetPromoByCategoryRequest {
    public String idCategory;
    public PromoEntity.PromoType promoType;
    public Long idState;
}
