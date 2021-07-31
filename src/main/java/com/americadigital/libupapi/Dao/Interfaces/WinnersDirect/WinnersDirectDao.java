package com.americadigital.libupapi.Dao.Interfaces.WinnersDirect;

import com.americadigital.libupapi.Dao.Entity.WinnerDirectEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WinnersDirectDao extends CrudRepository<WinnerDirectEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner_direct` WHERE id_user=?1 ORDER BY date DESC")
    List<WinnerDirectEntity> getAllHistoryWinnerDirectByIdUser(String idUser);
}
