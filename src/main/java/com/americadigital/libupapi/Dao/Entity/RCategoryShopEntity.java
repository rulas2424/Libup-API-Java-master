package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "r_category_shop")
@Data
public class RCategoryShopEntity {
    @Id
    public String idRcategory;

    @Column(name = "id_shop")
    @NotEmpty(message = "El id de comercio es requerido.")
    public String idShop;


    @Column(name = "id_category")
    @NotEmpty(message = "El id de categoría es requerido.")
    public String idCategory;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_category", insertable = false, updatable = false)
    @JsonIgnore
    public CategoryEntity category;

    public RCategoryShopEntity(String idRcategory, @NotEmpty(message = "El id de comercio es requerido.") String idShop, @NotEmpty(message = "El id de categoría es requerido.") String idCategory) {
        this.idRcategory = idRcategory;
        this.idShop = idShop;
        this.idCategory = idCategory;
    }

    protected  RCategoryShopEntity(){}
}
