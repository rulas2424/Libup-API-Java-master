package com.americadigital.libupapi.Dao.Interfaces.Shop;

import com.americadigital.libupapi.Dao.Entity.ShopEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface ShopDao extends CrudRepository<ShopEntity, String> {
    Optional<ShopEntity> findByIdShop(String idShop);
}
