package com.americadigital.libupapi.Pojos.RShedule;

import com.americadigital.libupapi.Dao.Entity.RSheduleEntity;

public class ScheduleGet {
    public String idShedule;
    public RSheduleEntity.WeekDay day;
    public String hourOpen;
    public String hourClose;
    public Boolean isClosed;
    public String idBranch;

    public ScheduleGet(String idShedule, RSheduleEntity.WeekDay day, String hourOpen, String hourClose, Boolean isClosed, String idBranch) {
        this.idShedule = idShedule;
        this.day = day;
        this.hourOpen = hourOpen;
        this.hourClose = hourClose;
        this.isClosed = isClosed;
        this.idBranch = idBranch;
    }
}
