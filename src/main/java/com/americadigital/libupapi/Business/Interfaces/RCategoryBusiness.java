package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.RCategory.RCategoryRequest;
import com.americadigital.libupapi.WsPojos.Response.RCategory.AddRCategoryResponse;
import com.americadigital.libupapi.WsPojos.Response.RCategory.RCategoryGetResponse;
import org.springframework.http.ResponseEntity;

public interface RCategoryBusiness {

    ResponseEntity<AddRCategoryResponse> addOrUpdateRelation(RCategoryRequest categoryRequest);

    ResponseEntity<RCategoryGetResponse> getListRelationsCategories(String idShop);
}
