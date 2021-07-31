package com.americadigital.libupapi.Dao.Interfaces.Winners;

import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface WinnersEntityDao extends CrudRepository<WinnersEntity, String> {

    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner` WHERE id_user=?1 ORDER BY date DESC")
    List<WinnersEntity> getAllHistoryWinnerByIdUser(String idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM `t_winner` WHERE id_winner=?1")
    Optional<WinnersEntity> findWinnerById(String idWinner);
}
