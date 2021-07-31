package com.americadigital.libupapi.Dao.Interfaces.Category;

import com.americadigital.libupapi.Dao.Entity.CategoryEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface CategoryDao extends CrudRepository<CategoryEntity, String> {
    Optional<CategoryEntity> findByIdCategory(String idCategory);

    @Query(
            value = "SELECT * FROM `c_category` WHERE c_category.active = 1;",
            nativeQuery = true)
    List<CategoryEntity> findAllCategoriesActives();

    @Query(
            value = "SELECT * FROM `c_category`;",
            nativeQuery = true)
    List<CategoryEntity> findAllCategories();

}
