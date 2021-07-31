package com.americadigital.libupapi.Dao.Interfaces.Contest;

import com.americadigital.libupapi.Dao.Entity.TContestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TContestPagDao extends PagingAndSortingRepository<TContestEntity, String> {
    @Query(
            value = "SELECT * FROM `t_contest` WHERE t_contest.id_admin=?1 AND t_contest.contain_audio=?2 ORDER BY t_contest.release_date DESC",
            nativeQuery = true)
    Page<TContestEntity> getContestLIstForIdAdmin(String idAdmin, boolean containAudio, Pageable pageable);

    @Query(
            value = "SELECT * FROM `t_contest` WHERE t_contest.id_broadcaster=?1 AND t_contest.contain_audio=?2 ORDER BY t_contest.release_date DESC",
            nativeQuery = true)
    Page<TContestEntity> getContestListByIdBroadcaster(String idBroadcaster, boolean containAudio, Pageable pageable);

//    @Query(
//            value = "SELECT * FROM `t_contest` WHERE t_contest.id_shop=?1 AND t_contest.contain_audio=?2 ORDER BY t_contest.release_date DESC",
//            nativeQuery = true)
//    Page<TContestEntity> getContestListForIdShop(String idShop, boolean containAudio, Pageable pageable);
@Query(
        value = "SELECT * FROM `t_contest` WHERE t_contest.id_shop=?1 ORDER BY t_contest.release_date DESC",
        nativeQuery = true)
Page<TContestEntity> getContestListForIdShop(String idShop, Pageable pageable);
}
