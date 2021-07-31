package com.americadigital.libupapi.WsPojos.Request.Branch;

import lombok.Data;

@Data
public class StatusBranchRequest {
    public String idBranch;
    public Boolean active;
    public String idShop;
}
