package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

import static javax.persistence.CascadeType.ALL;

@Table(name = "t_promo")
@Entity
public class PromoEntity {
    @Id
    public String idPromo;

    @Column(name = "name")
    @Size(min = 1, max = 100, message = "Nombre promoción debe tener entre 1 y 30 caracteres.")
    @NotEmpty(message = "El nombre de la promoción es requerido.")
    public String name;


    @Column(name = "description")
    public String description;

    @Column(name = "url_terms")
    @Size(min = 1, max = 200, message = "Url terminos debe tener entre 1 y 200 caracteres.")
    @NotEmpty(message = "El url terminos es requerido.")
    public String urlTerms;

    @Column(name = "url_promo")
    public String urlPromo;

    @Column(name = "image")
    public String image;

    @Column(name = "release_date")
    public Date releaseDate;

    @Column(name = "due_date")
    public Date dueDate;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public PromoType promoType;

    @Column(name = "code")
    public String code;

    @Column(name = "id_consolacion")
    public String awardConsolation;

    @Column(name = "active")
    public boolean active;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "award_type")
    @Enumerated(value = EnumType.STRING)
    public AwardType awardType;

    @Column(name = "with_notify")
    public boolean withNotify;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @Column(name = "seconds")
    public Integer seconds;

    @Column(name = "id_shop")
    @NotEmpty(message = "El id de comercio es requerido.")
    public String idShop;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "promoEntity")
    @JsonManagedReference
    public List<RPromoBranchEntity> rPromoBranchEntity;

    @OneToMany(mappedBy = "promoEntity", cascade = ALL, orphanRemoval = true)
    @JsonManagedReference
    public List<RCategoryPromoEntity> rCategoryPromoEntities;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    @JsonIgnore
    public ShopEntity shopEntity;

    public enum PromoType {
        Promoción,
        Descuento,
        Premio
    }

    public enum AwardType {
        Ninguno,
        Ticktear,
        Directo,
        Audio
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public AwardType getAwardType() {
        return awardType;
    }

    public void setAwardType(AwardType awardType) {
        this.awardType = awardType;
    }

    public boolean isWithNotify() {
        return withNotify;
    }

    public void setWithNotify(boolean withNotify) {
        this.withNotify = withNotify;
    }

    public String getIdBroadcaster() {
        return idBroadcaster;
    }

    public void setIdBroadcaster(String idBroadcaster) {
        this.idBroadcaster = idBroadcaster;
    }

    public String getAwardConsolation() {
        return awardConsolation;
    }

    public void setAwardConsolation(String awardConsolation) {
        this.awardConsolation = awardConsolation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ShopEntity getShopEntity() {
        return shopEntity;
    }

    public void setShopEntity(ShopEntity shopEntity) {
        this.shopEntity = shopEntity;
    }

    public List<RCategoryPromoEntity> getrCategoryPromoEntities() {
        return rCategoryPromoEntities;
    }

    public void setrCategoryPromoEntities(List<RCategoryPromoEntity> rCategoryPromoEntities) {
        this.rCategoryPromoEntities = rCategoryPromoEntities;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(String idPromo) {
        this.idPromo = idPromo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlTerms() {
        return urlTerms;
    }

    public void setUrlTerms(String urlTerms) {
        this.urlTerms = urlTerms;
    }

    public String getUrlPromo() {
        return urlPromo;
    }

    public void setUrlPromo(String urlPromo) {
        this.urlPromo = urlPromo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }


    public PromoType getPromoType() {
        return promoType;
    }

    public void setPromoType(PromoType promoType) {
        this.promoType = promoType;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public List<RPromoBranchEntity> getrPromoBranchEntity() {
        return rPromoBranchEntity;
    }

    public void setrPromoBranchEntity(List<RPromoBranchEntity> rPromoBranchEntity) {
        this.rPromoBranchEntity = rPromoBranchEntity;
    }
}
