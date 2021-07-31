package com.americadigital.libupapi.WsPojos.Request.Branch;

import com.americadigital.libupapi.Dao.Entity.BranchEntity;
import lombok.Data;

import java.util.Optional;

@Data
public class AddBranchRequest {
    public String address;
    public Optional<String> phoneNumber;
    public BranchEntity.BranchType branchType;
    public String latitude;
    public String longitude;
    public String idShop;
    public Long idState;
}
