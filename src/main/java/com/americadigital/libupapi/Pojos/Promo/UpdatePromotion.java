package com.americadigital.libupapi.Pojos.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;

import java.util.Optional;

public class UpdatePromotion {
    public String name;
    public Optional<String> description;
    public String urlTerms;
    public String urlPromo;
    public Optional<String> image;
    public String releaseDate;
    public String dueDate;
    public PromoEntity.PromoType promoType;
    public String code;
    public Optional<String> consolationAward;
    public Optional<PromoEntity.AwardType> awardType;
    public Optional<Boolean> withNotify;
    public Optional<String> idBroadcaster;
    public Optional<Integer> seconds;

    public UpdatePromotion(String name, Optional<String> description, String urlTerms, String urlPromo, Optional<String> image, String releaseDate, String dueDate, PromoEntity.PromoType promoType, String code, Optional<String> consolationAward, Optional<PromoEntity.AwardType> awardType, Optional<Boolean> withNotify, Optional<String> idBroadcaster, Optional<Integer> seconds) {
        this.name = name;
        this.description = description;
        this.urlTerms = urlTerms;
        this.urlPromo = urlPromo;
        this.image = image;
        this.releaseDate = releaseDate;
        this.dueDate = dueDate;
        this.promoType = promoType;
        this.code = code;
        this.consolationAward = consolationAward;
        this.awardType = awardType;
        this.withNotify = withNotify;
        this.idBroadcaster = idBroadcaster;
        this.seconds = seconds;
    }
}
