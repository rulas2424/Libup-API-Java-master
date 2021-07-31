package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.BranchEntity;

public class SucursalesPromoShopWinners {
    public String idBranch;
    public String address;
    public String phoneNumber;
    public BranchEntity.BranchType branchType;
    public String latitud;
    public String longitud;

    public SucursalesPromoShopWinners(String idBranch, String address, String phoneNumber, BranchEntity.BranchType branchType, String latitud, String longitud) {
        this.idBranch = idBranch;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchType = branchType;
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
