package com.americadigital.libupapi.WsPojos.Request.Winners;

import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import lombok.Data;

@Data
public class WinnersRequest {
    public String idBroadcaster;
    public WinnersEntity.TypeWinner typeWinner;
    public Integer page;
    public Integer maxResults;
}
