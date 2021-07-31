package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "r_category_promo")
@Entity
public class RCategoryPromoEntity {
    @Id
    public String idRcategorypromo;

    @Column(name = "id_promo")
    @NotEmpty(message = "Id de la promoción es requerido.")
    public String idPromo;

    @Column(name = "id_category")
    @NotEmpty(message = "Id de la categoría es requerido.")
    public String idCategory;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_promo", insertable = false, updatable = false)
    public PromoEntity promoEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    @JsonIgnore
    public CategoryEntity categoryEntity;

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public String getIdRcategorypromo() {
        return idRcategorypromo;
    }

    public void setIdRcategorypromo(String idRcategorypromo) {
        this.idRcategorypromo = idRcategorypromo;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(String idPromo) {
        this.idPromo = idPromo;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public PromoEntity getPromoEntity() {
        return promoEntity;
    }

    public void setPromoEntity(PromoEntity promoEntity) {
        this.promoEntity = promoEntity;
    }
}
