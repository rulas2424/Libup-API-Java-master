package com.americadigital.libupapi.Pojos.Shop;

public class ShopGetAll {
    public String id_shop;
    public String name;
    public Boolean active;
    public String logotype;
    public String waterMark;
    public Integer sucursales;
    public String urlCommerce;

    public ShopGetAll(String id_shop, String name, Boolean active, String logotype, String waterMark, Integer sucursales, String urlCommerce) {
        this.id_shop = id_shop;
        this.name = name;
        this.active = active;
        this.logotype = logotype;
        this.waterMark = waterMark;
        this.sucursales = sucursales;
        this.urlCommerce = urlCommerce;
    }
}
