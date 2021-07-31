package com.americadigital.libupapi.WsPojos.Response.Contest;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;

public class PromoContestResponse {
    public String idPromo;
    public String namePromo;
    public String description;
    public String urlTerms;
    public String urlPromo;
    public String image;
    public String releaseDate;
    public String dueDate;
    public PromoEntity.PromoType  promoType;
    public String code;
    public Boolean active;
    public String idShop;
    public Integer seconds;
    public PromoEntity.AwardType awardType;

    public PromoContestResponse(String idPromo, String namePromo, String description, String urlTerms, String urlPromo, String image, String releaseDate, String dueDate, PromoEntity.PromoType promoType, String code, Boolean active, String idShop, Integer seconds, PromoEntity.AwardType awardType) {
        this.idPromo = idPromo;
        this.namePromo = namePromo;
        this.description = description;
        this.urlTerms = urlTerms;
        this.urlPromo = urlPromo;
        this.image = image;
        this.releaseDate = releaseDate;
        this.dueDate = dueDate;
        this.promoType = promoType;
        this.code = code;
        this.active = active;
        this.idShop = idShop;
        this.seconds = seconds;
        this.awardType = awardType;
    }
}
