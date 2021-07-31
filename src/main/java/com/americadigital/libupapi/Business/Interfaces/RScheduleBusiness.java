package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.RShedule.AddScheduleRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.RShedule.GetSchedulesResponse;
import org.springframework.http.ResponseEntity;

public interface RScheduleBusiness {
    ResponseEntity<HeaderResponse> addSchedule(AddScheduleRequest scheduleRequest);

    ResponseEntity<HeaderResponse> updateSchedule(AddScheduleRequest scheduleRequest);

    ResponseEntity<GetSchedulesResponse> getAllSchedulesByIdBranch(String idBranch);
}
