package com.americadigital.libupapi.Dao.Interfaces.WinnersDirect;

import com.americadigital.libupapi.Dao.Entity.WinnerDirectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WinnersDirectPagDao extends PagingAndSortingRepository<WinnerDirectEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner_direct` WHERE id_shop=?1 ORDER BY date DESC")
    Page<WinnerDirectEntity> getAllWinnersDirectByShop(String idShop, Pageable pageable);
}
