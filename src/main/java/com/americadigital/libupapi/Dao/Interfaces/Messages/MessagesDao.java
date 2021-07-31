package com.americadigital.libupapi.Dao.Interfaces.Messages;

import com.americadigital.libupapi.Dao.Entity.MessagesEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MessagesDao extends CrudRepository<MessagesEntity, String> {
    @Query(
            value = "select * from t_messages WHERE id_channel=?1 AND is_deleted = 0 AND active = 1 ORDER BY date_hour",
            nativeQuery = true)
    List<MessagesEntity> findAllMessagesByIdChannel(String idChannel);

    Optional<MessagesEntity> findByIdChannel(String idChannel);
}
