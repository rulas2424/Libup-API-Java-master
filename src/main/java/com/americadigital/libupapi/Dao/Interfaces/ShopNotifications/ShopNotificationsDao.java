package com.americadigital.libupapi.Dao.Interfaces.ShopNotifications;

import com.americadigital.libupapi.Dao.Entity.ShopNotificationsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface ShopNotificationsDao extends CrudRepository<ShopNotificationsEntity, Long> {
    @Query(value = "SELECT * FROM `t_shop_notifications` WHERE uid_shop=?1 AND (created_time BETWEEN ?2 AND ?3)",
            nativeQuery = true)
    List<ShopNotificationsEntity> getNotificationsUsed(String idCommerce, Date createdTime, Date expirationDate);
}
