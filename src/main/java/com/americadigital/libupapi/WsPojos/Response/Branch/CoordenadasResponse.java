package com.americadigital.libupapi.WsPojos.Response.Branch;

import com.americadigital.libupapi.Utils.HeaderGeneric;

public class CoordenadasResponse {
    public HeaderGeneric header;
    public BranchNearestResponse data;

    public CoordenadasResponse(HeaderGeneric header, BranchNearestResponse data) {
        this.header = header;
        this.data = data;
    }
}
