package com.americadigital.libupapi.WsPojos.Request.RCategory;

import com.americadigital.libupapi.Pojos.RCategory.RCategoryAdd;
import lombok.Data;

import java.util.List;

@Data
public class RCategoryRequest {
    public List<RCategoryAdd> relations;
    public String idShop;
}
