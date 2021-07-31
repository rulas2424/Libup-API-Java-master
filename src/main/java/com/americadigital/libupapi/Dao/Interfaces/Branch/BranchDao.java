package com.americadigital.libupapi.Dao.Interfaces.Branch;

import com.americadigital.libupapi.Dao.Entity.BranchEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface BranchDao extends CrudRepository<BranchEntity, String> {
    @Query(nativeQuery = true, value = "SELECT * FROM `t_branch` WHERE t_branch.is_deleted = 0 AND t_branch.id_shop =?1")
    Optional<List<BranchEntity>> findBranchByIdShop(String idShop);


    @Query(nativeQuery = true, value = "SELECT * FROM `t_branch` WHERE t_branch.is_deleted = 0 AND t_branch.id_shop =?1")
    List<BranchEntity> findBranchAll(String idShop);


    @Query(nativeQuery = true, value = "SELECT * FROM `t_branch` WHERE t_branch.is_deleted = 0 AND t_branch.id_shop =?1 and t_branch.active = 1")
    List<BranchEntity> findBranchAllActives(String idShop);


    @Query(nativeQuery = true, value = "SELECT * FROM `t_branch` WHERE t_branch.is_deleted = 0 AND t_branch.id_shop =?1 and t_branch.active = 1")
    List<BranchEntity> findActivesBranchesByIdShop(String idShop);


    Optional<BranchEntity> findByIdBranch(String idBranch);


    @Query(nativeQuery = true, value = "SELECT *, ( 6371000 * acos(cos(radians(?1)) * cos(radians(latitud)) * cos(radians(longitud) - radians(?2)) + sin(radians(?1)) * sin(radians(latitud)))) AS distance FROM t_branch WHERE t_branch.is_deleted = 0 AND t_branch.active = 1 HAVING distance < ?3 ORDER BY distance")
    List<BranchEntity> findNearestByCoordenates(String latitude, String longitude, double rangeMetter);

    @Query(nativeQuery = true, value = "SELECT *, ( 6371 * acos(cos(radians(?1)) * cos(radians(latitud)) * cos(radians(longitud) - radians(?2)) + sin(radians(?1)) * sin(radians(latitud)))) AS distance FROM t_branch WHERE t_branch.is_deleted = 0 HAVING distance < ?3 ORDER BY distance")
    List<BranchEntity> findBranchNearestByCoordinates(String latitude, String longitude, double rangeMetter);


}
