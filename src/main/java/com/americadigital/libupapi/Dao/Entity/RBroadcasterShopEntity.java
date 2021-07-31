package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Table(name = "r_shop_broadcaster")
@Entity
@Data
public class RBroadcasterShopEntity {
    @Id
    public String idRelation;

    @Column(name = "id_shop")
    public String idShop;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @Column(name = "active")
    public boolean active;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_broadcaster", insertable = false, updatable = false)
    @JsonIgnore
    public BroadcasterEntity broadcasterEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    @JsonIgnore
    public ShopEntity shopEntity;
}
