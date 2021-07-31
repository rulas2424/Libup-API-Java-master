package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_shop_subscription")
public class ShopSubscriptionEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long id_suscription;

    @Column(name = "uid_shop")
    public String uidShop;

    @Column(name = "authorization")
    public String authorization;

    @Column(name = "openpay_transaction")
    public String openPayTransaction;

    @Column(name = "created_time")
    public Date createdTime;

    @Column(name = "import")
    public Float importe;

    @Column(name = "expiration_date")
    public Date expirationDate;

    @Column(name = "notifications_allowed")
    public int notificationsAllowed;


}
