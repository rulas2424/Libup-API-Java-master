package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_shop_notifications")
public class ShopNotificationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id_shop_notification;

    @Column(name = "uid_shop")
    public String uidShop;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public TypeNotify typeNotify;

    @Column(name = "created_time")
    public Date createdTime;

    public enum TypeNotify {
        TickTear, Audio, Directo, Promociones, Descuentos
    }
}
