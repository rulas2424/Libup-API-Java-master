package com.americadigital.libupapi.Pojos.RBroadcasterShop;

public class GetRelationsBroadcasterShop {
    public String idRelation;
    public String idShop;
    public String shopName;
    public String idBroadcaster;
    public String nameBroadcaster;
    public boolean active;

    public GetRelationsBroadcasterShop(String idRelation, String idShop, String shopName, String idBroadcaster, String nameBroadcaster, boolean active) {
        this.idRelation = idRelation;
        this.idShop = idShop;
        this.shopName = shopName;
        this.idBroadcaster = idBroadcaster;
        this.nameBroadcaster = nameBroadcaster;
        this.active = active;
    }
}
