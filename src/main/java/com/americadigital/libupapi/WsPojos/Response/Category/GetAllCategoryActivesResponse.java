package com.americadigital.libupapi.WsPojos.Response.Category;

import com.americadigital.libupapi.Pojos.Category.CategoriesActives;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetAllCategoryActivesResponse {
    public HeaderGeneric header;
    public CategoriesActives data;

    public GetAllCategoryActivesResponse(HeaderGeneric header, CategoriesActives data) {
        this.header = header;
        this.data = data;
    }
}
