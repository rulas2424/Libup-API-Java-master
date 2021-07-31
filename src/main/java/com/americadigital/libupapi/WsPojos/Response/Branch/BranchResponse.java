package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Pojos.Branch.BranchPojo;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class BranchResponse {
    public HeaderGeneric header;
    public BranchPojo data;

    public BranchResponse(HeaderGeneric header, BranchPojo data) {
        this.header = header;
        this.data = data;
    }
}
