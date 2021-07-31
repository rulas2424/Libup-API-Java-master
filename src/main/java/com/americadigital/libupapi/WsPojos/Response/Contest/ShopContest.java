package com.americadigital.libupapi.WsPojos.Response.Contest;

public class ShopContest {
    public String idShop;
    public String nameShop;
    public boolean active;
    public String image;

    public ShopContest(String idShop, String nameShop, boolean active, String image) {
        this.idShop = idShop;
        this.nameShop = nameShop;
        this.active = active;
        this.image = image;
    }
}
