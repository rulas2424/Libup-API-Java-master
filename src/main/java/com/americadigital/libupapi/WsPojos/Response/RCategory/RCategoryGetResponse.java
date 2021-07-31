package com.americadigital.libupapi.WsPojos.Response.RCategory;

import com.americadigital.libupapi.Pojos.RCategory.RCategoryGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class RCategoryGetResponse {
    public HeaderGeneric header;
    public List<RCategoryGet> relations;

    public RCategoryGetResponse(HeaderGeneric header, List<RCategoryGet> relations) {
        this.header = header;
        this.relations = relations;
    }
}
