package com.americadigital.libupapi.Dao.Interfaces.User;

import com.americadigital.libupapi.Dao.Entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserPagDao extends PagingAndSortingRepository<UserEntity, String> {

    @Query(
            value = "SELECT * FROM `t_user` WHERE t_user.is_deleted = 0",
            nativeQuery = true)
    Page<UserEntity> findAllUsers(Pageable pageable);

    @Query(value = "SELECT * FROM `t_user` WHERE t_user.is_deleted = 0 AND t_user.name like %:searchtext% OR t_user.email like %:searchtext% OR t_user.last_name like %:searchtext% OR t_user.phone_number like %:searchtext%",
            nativeQuery = true)
    Page<UserEntity> findUsersByAllColumns(@Param("searchtext") String searchtext, Pageable pageable);




}
