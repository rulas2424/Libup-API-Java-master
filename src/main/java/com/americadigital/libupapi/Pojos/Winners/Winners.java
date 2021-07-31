package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.WinnersEntity;

public class Winners {
    public String idWinner;
    public WinnersEntity.StatusWinner statusWinner;
    public ContestWinner contestWinner;
    public UserWinner userWinner;
    public int tickCount;
    public String dateWinner;
    public WinnersEntity.TypeWinner typeWinner;
    public String idBroadcaster;
    public String nameBroadcaster;

    public Winners(String idWinner, WinnersEntity.StatusWinner statusWinner, ContestWinner contestWinner, UserWinner userWinner, int tickCount, String dateWinner, WinnersEntity.TypeWinner typeWinner, String idBroadcaster, String nameBroadcaster) {
        this.idWinner = idWinner;
        this.statusWinner = statusWinner;
        this.contestWinner = contestWinner;
        this.userWinner = userWinner;
        this.tickCount = tickCount;
        this.dateWinner = dateWinner;
        this.typeWinner = typeWinner;
        this.idBroadcaster = idBroadcaster;
        this.nameBroadcaster = nameBroadcaster;
    }
}
