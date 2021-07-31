package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Dao.Entity.BranchEntity;
import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;
import com.americadigital.libupapi.Dao.Entity.ShopEntity;

import java.util.List;

public class BranchNearestResponse {
    public String idBranch;
    public String address;
    public String phoneNumber;
    public boolean active;
    public BranchEntity.BranchType type;
    public ShopEntity shopEntity;
    public boolean isDeleted;
    public String latitude;
    public String longitude;
    public List<RSheduleEntity> shedules;
    public Long idState;

    public BranchNearestResponse(String idBranch, String address, String phoneNumber, boolean active, BranchEntity.BranchType type, ShopEntity shopEntity, boolean isDeleted, String latitude, String longitude, List<RSheduleEntity> shedules, Long idState) {
        this.idBranch = idBranch;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.type = type;
        this.shopEntity = shopEntity;
        this.isDeleted = isDeleted;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shedules = shedules;
        this.idState = idState;
    }
}
