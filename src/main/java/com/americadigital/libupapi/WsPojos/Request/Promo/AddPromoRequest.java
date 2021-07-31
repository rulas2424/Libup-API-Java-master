package com.americadigital.libupapi.WsPojos.Request.Promo;

import com.americadigital.libupapi.Pojos.Promo.AddPromotion;
import lombok.Data;

import java.util.List;

@Data
public class AddPromoRequest {
    public AddPromotion promotion;
    public List<String> idBranches;
    public List<String> idCategories;
}
