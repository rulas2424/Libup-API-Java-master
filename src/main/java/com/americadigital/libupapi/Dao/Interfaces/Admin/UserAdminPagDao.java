package com.americadigital.libupapi.Dao.Interfaces.Admin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserAdminPagDao extends PagingAndSortingRepository<UserAdminEntity, String> {

    @Query(
            value = "SELECT * FROM `t_admin` WHERE t_admin.is_deleted = 0 AND t_admin.type != 'SuperAdmin'",
            nativeQuery = true)
    Page<UserAdminEntity> findAllUsersAdmin(Pageable pageable);


    @Query(value = "SELECT * FROM `t_admin` WHERE t_admin.is_deleted = 0 AND t_admin.type != 'SuperAdmin' AND (t_admin.name like %:searchtext% OR t_admin.last_name like %:searchtext% OR t_admin.email like %:searchtext% OR t_admin.phone_number like %:searchtext% )",
            nativeQuery = true)
    Page<UserAdminEntity> findUsersAdminByAllColumns(@Param("searchtext") String searchtext, Pageable pageable);
}
