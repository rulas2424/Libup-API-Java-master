package com.americadigital.libupapi.WsPojos.Request.RShedule;

import com.americadigital.libupapi.Pojos.RShedule.ScheduleAdd;
import lombok.Data;

import java.util.List;

@Data
public class AddScheduleRequest {
    public List<ScheduleAdd> schedules;
    public String idBranch;
}
