package com.americadigital.libupapi.WsPojos.Request.Category;

import lombok.Data;

@Data
public class CategoryStatusRequest {
    public String idCategory;
    public Boolean active;
}
