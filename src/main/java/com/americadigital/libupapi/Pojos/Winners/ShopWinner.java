package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.RPromoBranchEntity;

import java.util.List;

public class ShopWinner {
    public String idShop;
    public String nameShop;
    public boolean active;
    public String image;
    public List<SucursalesPromoShopWinners> branchesForAward;

    public ShopWinner(String idShop, String nameShop, boolean active, String image, List<SucursalesPromoShopWinners> branchesForAward) {
        this.idShop = idShop;
        this.nameShop = nameShop;
        this.active = active;
        this.image = image;
        this.branchesForAward = branchesForAward;
    }
}
