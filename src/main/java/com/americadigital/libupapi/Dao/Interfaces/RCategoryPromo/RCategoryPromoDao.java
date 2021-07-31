package com.americadigital.libupapi.Dao.Interfaces.RCategoryPromo;

import com.americadigital.libupapi.Dao.Entity.RCategoryPromoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RCategoryPromoDao extends CrudRepository<RCategoryPromoEntity, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM `r_category_promo` WHERE r_category_promo.id_promo=?1")
    List<RCategoryPromoEntity> findListRelationsPromoCategory(String idPromo);

    @Query(nativeQuery = true, value = "SELECT * FROM `r_category_promo` WHERE r_category_promo.id_category=?1")
    List<RCategoryPromoEntity> findListPromosByCategory(String idCategory);
}
