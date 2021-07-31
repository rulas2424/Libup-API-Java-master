package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Pojos.Branch.BranchList;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class BranchGetResponse {
    public HeaderGeneric header;
    public BranchList data;

    public BranchGetResponse(HeaderGeneric header, BranchList data) {
        this.header = header;
        this.data = data;
    }
}
