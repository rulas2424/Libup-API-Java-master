package com.americadigital.libupapi.WsPojos.Request.Shop;

public class AddShopRequest {
    public String name;
    public String logotype;
    public String waterMark;
    public String urlCommerce;

    public String getUrlCommerce() {
        return urlCommerce;
    }

    public void setUrlCommerce(String urlCommerce) {
        this.urlCommerce = urlCommerce;
    }

    public String getWaterMark() {
        return waterMark;
    }

    public void setWaterMark(String waterMark) {
        this.waterMark = waterMark;
    }

    public String getLogotype() {
        return logotype;
    }

    public void setLogotype(String logotype) {
        this.logotype = logotype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
