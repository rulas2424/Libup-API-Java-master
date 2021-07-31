package com.americadigital.libupapi.Dao.Interfaces.Broadcaster;

import com.americadigital.libupapi.Dao.Entity.BroadcasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BroadcasterDao extends CrudRepository<BroadcasterEntity, String> {
    @Query(
            value = "select * from t_broadcaster WHERE is_deleted = 0 AND active = 1",
            nativeQuery = true)
    List<BroadcasterEntity> findAllBroadcasterActives();
}
