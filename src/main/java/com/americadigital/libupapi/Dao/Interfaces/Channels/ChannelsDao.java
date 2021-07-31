package com.americadigital.libupapi.Dao.Interfaces.Channels;

import com.americadigital.libupapi.Dao.Entity.ChannelsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChannelsDao extends CrudRepository<ChannelsEntity, String> {
    @Query(
            value = "select * from t_channels WHERE is_deleted = 0 AND active = 1",
            nativeQuery = true)
    List<ChannelsEntity> findAllChannelsActives();
}
