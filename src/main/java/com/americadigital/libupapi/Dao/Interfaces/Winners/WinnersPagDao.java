package com.americadigital.libupapi.Dao.Interfaces.Winners;

import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface WinnersPagDao extends PagingAndSortingRepository<WinnersEntity, String> {
    @Query(
            value = "SELECT * FROM `t_winner` WHERE type =?1 ORDER BY date DESC",
            nativeQuery = true)
    Page<WinnersEntity> getAllListWinners(String typeWinner, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner` WHERE id_broadcaster=?1 AND type =?2 ORDER BY date DESC")
    Page<WinnersEntity> getAllWinnersByBroadcaster(String idBroadcaster, String typeWinner, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner` WHERE id_shop=?1 ORDER BY date DESC")
    Page<WinnersEntity> getAllWinnersByShop(String idShop, Pageable pageable);
}
