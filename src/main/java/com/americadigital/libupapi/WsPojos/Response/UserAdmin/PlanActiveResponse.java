package com.americadigital.libupapi.WsPojos.Response.UserAdmin;

import com.americadigital.libupapi.Pojos.UserAdmin.ActivePlan;
import com.americadigital.libupapi.Utils.HeaderGeneric;

public class PlanActiveResponse {
    public HeaderGeneric header;
    public ActivePlan data;

    public PlanActiveResponse(HeaderGeneric header, ActivePlan data) {
        this.header = header;
        this.data = data;
    }
}
