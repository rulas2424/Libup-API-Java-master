package com.americadigital.libupapi.Pojos.RShedule;

import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;

import java.util.Optional;

public class ScheduleAdd {
    public RSheduleEntity.WeekDay weekDay;
    public Optional<String> hourOpen;
    public Optional<String> hourClose;
    public Boolean isClosed;

    public ScheduleAdd(RSheduleEntity.WeekDay weekDay, Optional<String> hourOpen, Optional<String> hourClose, Boolean isClosed) {
        this.weekDay = weekDay;
        this.hourOpen = hourOpen;
        this.hourClose = hourClose;
        this.isClosed = isClosed;
    }
}
