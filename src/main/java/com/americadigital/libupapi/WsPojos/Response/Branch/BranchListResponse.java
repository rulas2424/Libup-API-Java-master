package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Pojos.Branch.BranchList;
import com.americadigital.libupapi.Pojos.UserAdmin.UserAdminRepo;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class BranchListResponse {
    public HeaderGeneric header;
    public List<BranchList> data;
    public List<UserAdminRepo> userAdmin;

    public BranchListResponse(HeaderGeneric header, List<BranchList> data, List<UserAdminRepo> userAdmin) {
        this.header = header;
        this.data = data;
        this.userAdmin = userAdmin;
    }
}
