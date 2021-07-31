package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Category.AddCategoryRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.CategoriesActivesRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.CategoryStatusRequest;
import com.americadigital.libupapi.WsPojos.Request.Category.UpdateCategoryRequest;
import com.americadigital.libupapi.WsPojos.Response.Category.CategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.Category.GetAllCategoryActivesResponse;
import com.americadigital.libupapi.WsPojos.Response.Category.GetAllCategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;

public interface CategoryBusiness {
    ResponseEntity<CategoryResponse> addCategory(AddCategoryRequest request);

    ResponseEntity<CategoryResponse> updateCategory(UpdateCategoryRequest request);

    ResponseEntity<HeaderResponse> changeActiveStatus(CategoryStatusRequest request);

    ResponseEntity<CategoryResponse> getCategoryById(String idCategory);

    ResponseEntity<GetAllCategoryResponse> getAllCategories();

    ResponseEntity<GetAllCategoryActivesResponse> getAllCategoriesActivesPanel();

    ResponseEntity<GetAllCategoryActivesResponse> getAllCategoryActives(CategoriesActivesRequest request);
}
