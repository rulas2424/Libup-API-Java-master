package com.americadigital.libupapi.Pojos.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import com.americadigital.libupapi.Pojos.RCategoryPromo.RCategoryPromoGet;
import com.americadigital.libupapi.Pojos.RPromoBrannch.GetAllPromoBranch;

import java.util.List;

public class PromoGetAll {
    public String idPromo;
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
    public boolean isDeleted;
    public String idShop;
    public String nameShop;
    public String awardConsolation;
    public List<GetAllPromoBranch> rPromoBranches;
    public List<RCategoryPromoGet> rCategoryPromos;
    public boolean isDateActive;
    public PromoEntity.AwardType awardType;
    public boolean withNotify;
    public String idBroadcaster;
    public Integer seconds;

    public PromoGetAll(String idPromo, String name, String description, String urlTerms, String urlPromo, String image, String releaseDate, String dueDate, PromoEntity.PromoType promoType, String code, boolean active, boolean isDeleted, String idShop, String nameShop, String awardConsolation, List<GetAllPromoBranch> rPromoBranches, List<RCategoryPromoGet> rCategoryPromos, boolean isDateActive, PromoEntity.AwardType awardType, boolean withNotify, String idBroadcaster, Integer seconds) {
        this.idPromo = idPromo;
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
        this.isDeleted = isDeleted;
        this.idShop = idShop;
        this.nameShop = nameShop;
        this.awardConsolation = awardConsolation;
        this.rPromoBranches = rPromoBranches;
        this.rCategoryPromos = rCategoryPromos;
        this.isDateActive = isDateActive;
        this.awardType = awardType;
        this.withNotify = withNotify;
        this.idBroadcaster = idBroadcaster;
        this.seconds = seconds;
    }
}
