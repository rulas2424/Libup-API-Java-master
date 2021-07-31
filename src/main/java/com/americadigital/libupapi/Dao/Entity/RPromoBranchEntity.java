package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(name = "r_promo_branch")
@Entity
public class RPromoBranchEntity {
    @Id
    public String idPromoBranch;

    @Column(name = "id_promo")
    @NotEmpty(message = "Id de la promoción es requerido.")
    public String idPromo;

    @Column(name = "id_branch")
    @NotEmpty(message = "El id de sucursal es requerido.")
    public String idBranch;

    @NotNull
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_promo", insertable = false, updatable = false)
    public PromoEntity promoEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_branch", insertable = false, updatable = false)
    @JsonIgnore
    public BranchEntity branchEntity;

    public BranchEntity getBranchEntity() {
        return branchEntity;
    }

    public void setBranchEntity(BranchEntity branchEntity) {
        this.branchEntity = branchEntity;
    }

    public String getIdPromoBranch() {
        return idPromoBranch;
    }

    public void setIdPromoBranch(String idPromoBranch) {
        this.idPromoBranch = idPromoBranch;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(String idPromo) {
        this.idPromo = idPromo;
    }

    public String getIdBranch() {
        return idBranch;
    }

    public void setIdBranch(String idBranch) {
        this.idBranch = idBranch;
    }

    public PromoEntity getPromoEntity() {
        return promoEntity;
    }

    public void setPromoEntity(PromoEntity promoEntity) {
        this.promoEntity = promoEntity;
    }
}
