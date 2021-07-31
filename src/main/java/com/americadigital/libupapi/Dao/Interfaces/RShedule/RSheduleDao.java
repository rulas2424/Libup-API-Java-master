package com.americadigital.libupapi.Dao.Interfaces.RShedule;

import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RSheduleDao extends CrudRepository<RSheduleEntity, String> {
    RSheduleEntity findByIdBranch(String idBranch);


    @Query(nativeQuery = true, value = "SELECT * FROM `r_schedule` WHERE r_schedule.id_branch=?1 ORDER BY day")
    List<RSheduleEntity> findListSheduleByIdBranch(String idBranch);
}
