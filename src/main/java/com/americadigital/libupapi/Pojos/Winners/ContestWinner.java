package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.TContestEntity;

public class ContestWinner {
    public String idContest;
    public String idAdmin;
    public AwardWinner awardWinner;
    public TContestEntity.StatusContest statusContest;
    public String idAcr;
    public String audioTitle;
    public String audio;
    public String releaseDate;
    public String dataType;
    public String bucketName;


    public ContestWinner(String idContest, String idAdmin, AwardWinner awardWinner, TContestEntity.StatusContest statusContest, String idAcr, String audioTitle, String audio, String releaseDate, String dataType, String bucketName) {
        this.idContest = idContest;
        this.idAdmin = idAdmin;
        this.awardWinner = awardWinner;
        this.statusContest = statusContest;
        this.idAcr = idAcr;
        this.audioTitle = audioTitle;
        this.audio = audio;
        this.releaseDate = releaseDate;
        this.dataType = dataType;
        this.bucketName = bucketName;
    }
}
