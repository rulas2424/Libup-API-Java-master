package com.americadigital.libupapi.WsPojos.Response.RShedule;

import com.americadigital.libupapi.Pojos.RShedule.ScheduleGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetSchedulesResponse {
    public HeaderGeneric header;
    public List<ScheduleGet> data;

    public GetSchedulesResponse(HeaderGeneric header, List<ScheduleGet> data) {
        this.header = header;
        this.data = data;
    }
}
