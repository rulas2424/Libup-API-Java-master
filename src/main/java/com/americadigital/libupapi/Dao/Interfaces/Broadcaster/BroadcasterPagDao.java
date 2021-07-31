package com.americadigital.libupapi.Dao.Interfaces.Broadcaster;

import com.americadigital.libupapi.Dao.Entity.BroadcasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface BroadcasterPagDao extends PagingAndSortingRepository<BroadcasterEntity, String> {
    @Query(
            value = "select * from t_broadcaster WHERE is_deleted = 0",
            nativeQuery = true)
    Page<BroadcasterEntity> findAllBroadcaster(Pageable pageable);

    @Query(value = "select * from t_broadcaster WHERE t_broadcaster.is_deleted = 0 AND t_broadcaster.name like %:searchtext%",
            nativeQuery = true)
    Page<BroadcasterEntity> searchBroadcaster(@Param("searchtext") String searchtext, Pageable pageable);
}
