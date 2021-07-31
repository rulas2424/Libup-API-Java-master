package com.americadigital.libupapi.Dao.Interfaces.ShopSubscription;

import com.americadigital.libupapi.Dao.Entity.ShopSubscriptionEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShopSubscriptionsDao extends CrudRepository<ShopSubscriptionEntity, Long> {
    @Query(value = "SELECT * FROM t_shop_subscription  WHERE id_suscription=(select max(id_suscription) from t_shop_subscription where uid_shop=?1)",
            nativeQuery = true)
    Optional<ShopSubscriptionEntity> getTheLastSubscription(String idCommerce);

    @Query(value = "SELECT * FROM `t_shop_subscription` WHERE id_suscription=?1 AND (now() BETWEEN t_shop_subscription.created_time AND t_shop_subscription.expiration_date)",
            nativeQuery = true)
    Optional<ShopSubscriptionEntity> checkActiveSubscriptionCommerce(Long idSuscription);
}
