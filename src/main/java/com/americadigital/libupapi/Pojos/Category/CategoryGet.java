package com.americadigital.libupapi.Pojos.Category;

public class CategoryGet {
    public String idCategory;
    public String name;
    public boolean active;

    public CategoryGet(String idCategory, String name, boolean active) {
        this.idCategory = idCategory;
        this.name = name;
        this.active = active;
    }

}
