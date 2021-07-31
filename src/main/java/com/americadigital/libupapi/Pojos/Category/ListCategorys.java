package com.americadigital.libupapi.Pojos.Category;

import com.americadigital.libupapi.Dao.Entity.CategoryEntity;

import java.util.List;

public class ListCategorys {
    public List<CategoryEntity> categoryList;

    public ListCategorys(List<CategoryEntity> categoryList) {
        this.categoryList = categoryList;
    }
}
