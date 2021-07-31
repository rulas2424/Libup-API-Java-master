package com.americadigital.libupapi.Dao.Interfaces.RPromoBranch;

import com.americadigital.libupapi.Dao.Entity.RPromoBranchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RPromoBranchDao extends CrudRepository<RPromoBranchEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM `r_promo_branch` WHERE r_promo_branch.id_promo=?1")
    List<RPromoBranchEntity> findListRelationsPromoBranch(String idPromo);
}
