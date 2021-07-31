package com.americadigital.libupapi.Dao.Interfaces.User;

import com.americadigital.libupapi.Dao.Entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserEntityDao extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdUser(String idUser);

    @Query(nativeQuery = true, value = "SELECT * FROM `t_user` WHERE t_user.active = 1")
    List<UserEntity> getUserActives();

    @Query(nativeQuery = true, value = "SELECT *, ( 6371 * acos(cos(radians(?1)) * cos(radians(latitude)) * cos(radians(longitude) - radians(?2)) + sin(radians(?1)) * sin(radians(latitude)))) AS distance FROM t_user WHERE t_user.is_deleted = 0 AND t_user.active = 1 HAVING distance < ?3 ORDER BY distance")
    List<UserEntity> findUsersByCoordinates(String latitude, String longitude, double range);

}
