package com.americadigital.libupapi.Dao.Interfaces.Shop;

import com.americadigital.libupapi.Dao.Entity.ShopEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ShopPagDao extends PagingAndSortingRepository<ShopEntity, String> {
    @Query(
            value = "select * from t_shop WHERE t_shop.is_deleted = 0",
            nativeQuery = true)
    Page<ShopEntity> findAllCommercesForPanel(Pageable pageable);


    @Query(
            value = "select * from t_shop WHERE t_shop.active = 1 AND t_shop.is_deleted = 0",
            nativeQuery = true)
    List<ShopEntity> findAllComercesActives();

    @Query(
            value = "select * from t_shop WHERE t_shop.is_deleted = 0",
            nativeQuery = true)
    List<ShopEntity> findAllCommerces();

    @Query(value = "select * from t_shop WHERE t_shop.is_deleted = 0 AND t_shop.name like %:searchtext%",
            nativeQuery = true)
    Page<ShopEntity> searchCommerces(@Param("searchtext") String searchtext, Pageable pageable);
}
