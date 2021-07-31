package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;

public class AwardWinner {
    public String idAward;
    public String name;
    public String description;
    public String urlTerms;
    public String urlPromo;
    public String image;
    public String releaseDate;
    public String dueDate;
    public PromoEntity.PromoType promoType;
    public String code;
    public boolean active;
    public ShopWinner shopWinner;


    public AwardWinner(String idAward, String name, String description, String urlTerms, String urlPromo, String image, String releaseDate, String dueDate, PromoEntity.PromoType promoType, String code, boolean active, ShopWinner shopWinner) {
        this.idAward = idAward;
        this.name = name;
        this.description = description;
        this.urlTerms = urlTerms;
        this.urlPromo = urlPromo;
        this.image = image;
        this.releaseDate = releaseDate;
        this.dueDate = dueDate;
        this.promoType = promoType;
        this.code = code;
        this.active = active;
        this.shopWinner = shopWinner;
    }
}
