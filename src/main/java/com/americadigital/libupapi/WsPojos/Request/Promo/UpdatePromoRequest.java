package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Pojos.Promo.UpdatePromotion;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePromoRequest {
    public String idPromotion;
    public UpdatePromotion promotion;
    public List<String> idBranches;
    public List<String> idCategories;
}
