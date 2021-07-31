package com.americadigital.libupapi.Dao.Interfaces.ContestDetail;

import com.americadigital.libupapi.Dao.Entity.ContestDetailEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContestDetailDao extends CrudRepository<ContestDetailEntity, String> {
    @Query(
            value = "Select t_contest_detail.* FROM t_contest_detail WHERE tick_count = (select max(tick_count) from t_contest_detail d where d.id_contest=?1 )",
            nativeQuery = true)
    ContestDetailEntity findUserWinner(String idContest);

    @Query(
            value = "SELECT * FROM `t_contest_detail` WHERE t_contest_detail.id_contest=?1",
            nativeQuery = true)
    List<ContestDetailEntity> findUsersContest(String idContest);
}
