package com.americadigital.libupapi.Dao.Interfaces.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface PromoDao extends CrudRepository<PromoEntity, String> {
    Optional<PromoEntity> findByIdPromo(String idPromo);

    @Query(value = "SELECT * FROM `t_promo`  WHERE type =?2 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.id_promo IN (?1) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    List<PromoEntity> findListPromosByIdAndType(List<String> idsPromos, String typePromo);

    @Query(value = "SELECT * FROM `t_promo`  WHERE type =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND (t_promo.name like %?2% OR t_promo.description like %?2%) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    List<PromoEntity> searchListPromosActivesWithType(String typePromo, String searchtext);

    @Query(value = "SELECT * FROM `t_promo`  WHERE id_promo =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.is_deleted = 0",
            nativeQuery = true)
    Optional<PromoEntity> checkActivePromo(String idPromo);

    @Query(value = "SELECT * FROM `t_promo` WHERE t_promo.type != 'Premio' AND t_promo.id_shop =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    List<PromoEntity> findAllPromosOrDiscountsByIdShop(String idShop);

    @Query(value = "SELECT * FROM `t_promo` WHERE id_consolacion=?1",
            nativeQuery = true)
    List<PromoEntity> findAwardsWithPromoOrDiscount(String idPromo);
}
