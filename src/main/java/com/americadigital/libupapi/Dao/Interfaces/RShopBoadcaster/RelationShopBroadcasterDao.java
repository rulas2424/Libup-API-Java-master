package com.americadigital.libupapi.Dao.Interfaces.RShopBoadcaster;

import com.americadigital.libupapi.Dao.Entity.RBroadcasterShopEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RelationShopBroadcasterDao extends CrudRepository<RBroadcasterShopEntity, String> {
    List<RBroadcasterShopEntity> findByIdShop(String idShop);

    @Query(value = "SELECT * FROM `r_shop_broadcaster` WHERE id_shop=?1 AND active =1",
            nativeQuery = true)
    List<RBroadcasterShopEntity> findRelationsActivesByIdShop(String idShop);

    @Query(value = "SELECT * FROM `r_shop_broadcaster` WHERE id_broadcaster=?1 and active=?2",
            nativeQuery = true)
    List<RBroadcasterShopEntity> findRelationsByIdBroadcaster(String idBroadcaster, boolean active);

    @Query(value = "SELECT * FROM `r_shop_broadcaster` WHERE id_broadcaster=?1 AND active =1",
            nativeQuery = true)
    List<RBroadcasterShopEntity> findRelationsActivesByIdBroadcaster(String idBroadcaster);

    @Query(value = "SELECT * FROM `r_shop_broadcaster` WHERE id_shop=?1 AND id_broadcaster =?2",
            nativeQuery = true)
    Optional<RBroadcasterShopEntity> existRelationShopBroadcaster(String idShop, String idBroadcaster);
}
