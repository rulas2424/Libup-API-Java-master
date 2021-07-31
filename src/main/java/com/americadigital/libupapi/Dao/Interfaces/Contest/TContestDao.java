package com.americadigital.libupapi.Dao.Interfaces.Contest;

import com.americadigital.libupapi.Dao.Entity.TContestEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TContestDao extends CrudRepository<TContestEntity, String> {
    Optional<TContestEntity> findByIdContest(String idContest);

    @Query(
            value = "SELECT * FROM `t_contest` WHERE id_acr=?1",
            nativeQuery = true)
    Optional<TContestEntity> findContestByAcr(String idAcr);

    @Query(
            value = "SELECT * FROM `t_contest` WHERE id_promo=?1 AND status = 'Creado' OR status = 'EnCurso'",
            nativeQuery = true)
    List<TContestEntity> findContestByIdPromo(String idPromo);
}
