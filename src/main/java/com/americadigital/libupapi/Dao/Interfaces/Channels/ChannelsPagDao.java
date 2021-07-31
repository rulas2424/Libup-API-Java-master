package com.americadigital.libupapi.Dao.Interfaces.Channels;

import com.americadigital.libupapi.Dao.Entity.ChannelsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ChannelsPagDao extends PagingAndSortingRepository<ChannelsEntity, String> {
    @Query(
            value = "select * from t_channels WHERE is_deleted = 0 AND id_broadcaster=?1",
            nativeQuery = true)
    Page<ChannelsEntity> findAllChannelsByIdBroadcaster(String idBroadcaster, Pageable pageable);

}
