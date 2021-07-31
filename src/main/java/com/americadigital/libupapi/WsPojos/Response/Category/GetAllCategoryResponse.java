package com.americadigital.libupapi.WsPojos.Response.Category;

import com.americadigital.libupapi.Pojos.Category.ListCategorys;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class GetAllCategoryResponse {
    public HeaderGeneric header;
    public ListCategorys data;

    public GetAllCategoryResponse(HeaderGeneric header, ListCategorys data) {
        this.header = header;
        this.data = data;
    }
}
