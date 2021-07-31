package com.americadigital.libupapi.WsPojos.Request.Contest;

import com.americadigital.libupapi.Dao.Entity.TContestEntity;
import lombok.Data;

@Data
public class ChangeStatusContestRequest {
    public String idContest;
    public TContestEntity.StatusContest statusContest;
}
