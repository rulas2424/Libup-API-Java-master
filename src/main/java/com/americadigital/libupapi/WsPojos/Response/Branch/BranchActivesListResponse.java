package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Pojos.Branch.Branches;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class BranchActivesListResponse {
    public HeaderGeneric header;
    public List<Branches> data;

    public BranchActivesListResponse(HeaderGeneric header, List<Branches> data) {
        this.header = header;
        this.data = data;
    }
}
