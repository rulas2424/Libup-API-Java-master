package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_shop")
@Data
public class ShopEntity {
    @Id
    public String idShop;

    @Column(name = "name")
    @Size(min = 1, max = 30, message = "Nombre debe tener entre 1 y 30 caracteres.")
    @NotEmpty(message = "El nombre es requerido.")
    public String name;

    @Column(name = "active")
    public boolean active;

    @Column(name = "image")
    public String image;

    @Column(name = "image_watermark")
    public String waterMark;

    @Column(name = "url_commerce")
    public String urlCommerce;

    @Column(name = "token_openpay")
    public String tokenOpenPay;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    @JsonIgnore
    public UserAdminEntity userAdminEntity;

}
