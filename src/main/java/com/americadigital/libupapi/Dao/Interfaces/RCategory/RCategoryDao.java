package com.americadigital.libupapi.Dao.Interfaces.RCategory;

import com.americadigital.libupapi.Dao.Entity.RCategoryShopEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RCategoryDao extends CrudRepository<RCategoryShopEntity, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM `r_category_shop` WHERE r_category_shop.id_shop=?1")
    List<RCategoryShopEntity> findListRelations(String idShop);

    List<RCategoryShopEntity> findByIdShop(String idShop);

}
