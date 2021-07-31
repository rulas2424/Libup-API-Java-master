package com.americadigital.libupapi.WsPojos.Response.Category;

import com.americadigital.libupapi.Dao.Entity.CategoryEntity;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class CategoryResponse {
    public HeaderGeneric header;
    public CategoryEntity data;

    public CategoryResponse(HeaderGeneric header, CategoryEntity data) {
        this.header = header;
        this.data = data;
    }
}
