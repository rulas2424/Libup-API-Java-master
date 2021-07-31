package com.americadigital.libupapi.Pojos.WinnersDirect;

import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import com.americadigital.libupapi.Pojos.Winners.AwardWinner;
import com.americadigital.libupapi.Pojos.Winners.UserWinner;

public class WinnersDirect {
    public String idWinner;
    public WinnersEntity.StatusWinner statusWinner;
    public AwardWinner awardWinner;
    public UserWinner userWinner;
    public String dateWinner;

    public WinnersDirect(String idWinner, WinnersEntity.StatusWinner statusWinner, AwardWinner awardWinner, UserWinner userWinner, String dateWinner) {
        this.idWinner = idWinner;
        this.statusWinner = statusWinner;
        this.awardWinner = awardWinner;
        this.userWinner = userWinner;
        this.dateWinner = dateWinner;
    }
}
