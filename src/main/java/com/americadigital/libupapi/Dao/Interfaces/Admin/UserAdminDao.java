package com.americadigital.libupapi.Dao.Interfaces.Admin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserAdminDao extends CrudRepository<UserAdminEntity, String> {
    Optional<UserAdminEntity> findByEmail(String email);

    Optional<UserAdminEntity> findByIdAdmin(String idAdmin);

    List<UserAdminEntity> findByIdShop(String idShop);
}
