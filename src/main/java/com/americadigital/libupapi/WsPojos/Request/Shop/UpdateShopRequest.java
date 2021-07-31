package com.americadigital.libupapi.WsPojos.Request.Shop;

import java.util.Optional;

public class UpdateShopRequest {
    public String id_shop;
    public String name;
    public Optional<String> logotype;
    public Optional<String> waterMark;
    public String urlCommerce;

    public String getUrlCommerce() {
        return urlCommerce;
    }

    public void setUrlCommerce(String urlCommerce) {
        this.urlCommerce = urlCommerce;
    }

    public Optional<String> getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(Optional<String> waterMark) {
        this.waterMark = waterMark;
    }

    public Optional<String> getLogotype() {
        return logotype;
    }

    public void setLogotype(Optional<String> logotype) {
        this.logotype = logotype;
    }

    public String getId_shop() {
        return id_shop;
    }

    public void setId_shop(String id_shop) {
        this.id_shop = id_shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
