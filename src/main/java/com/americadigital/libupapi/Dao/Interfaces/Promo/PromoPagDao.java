package com.americadigital.libupapi.Dao.Interfaces.Promo;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PromoPagDao extends PagingAndSortingRepository<PromoEntity, String> {
    @Query(value = "SELECT * FROM `t_promo` WHERE t_promo.id_shop =?1 AND t_promo.is_deleted = 0 AND t_promo.type =?2 ORDER BY t_promo.active = 1 DESC",
            nativeQuery = true)
    Page<PromoEntity> findAllPromoByIdShop(String idShop, String typePromo, Pageable pageable);

    @Query(value = "SELECT * FROM `t_promo` WHERE t_promo.award_type = 'Directo' AND t_promo.id_shop =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    Page<PromoEntity> findAllAwardsDirectosByIdShop(String idShop, Pageable pageable);


    @Query(value = "SELECT * FROM `t_promo` WHERE t_promo.award_type = 'Audio' AND t_promo.id_shop =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    Page<PromoEntity> findAllAwardsAudioByIdShop(String idShop, Pageable pageable);

    @Query(value = "SELECT * FROM `t_promo` WHERE t_promo.type=?2 AND t_promo.id_shop =?1 AND (now() BETWEEN t_promo.release_date AND t_promo.due_date) AND t_promo.active = 1 AND t_promo.is_deleted = 0",
            nativeQuery = true)
    Page<PromoEntity> findAllPromosOrDiscountsByIdShop(String idShop, String promoType, Pageable pageable);
}
