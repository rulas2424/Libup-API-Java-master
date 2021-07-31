package com.americadigital.libupapi.WsPojos.Response.Contest;

import com.americadigital.libupapi.Dao.Entity.TContestEntity;

public class ContestResponse {
   public String idContest;
   public String idAdmin;
   public String nameAdmin;
   public PromoContestResponse promoContestResponse;
   public TContestEntity.StatusContest statusContest;
   public String idAcr;
   public String audioTitle;
   public String audio;
   public String releaseDate;
   public String dataType;
   public String bucketName;
   public String idBroadcaster;
   public String nameBroadcaster;
   public boolean containAudio;

    public ContestResponse(String idContest, String idAdmin, String nameAdmin, PromoContestResponse promoContestResponse, TContestEntity.StatusContest statusContest, String idAcr, String audioTitle, String audio, String releaseDate, String dataType, String bucketName, String idBroadcaster, String nameBroadcaster, boolean containAudio) {
        this.idContest = idContest;
        this.idAdmin = idAdmin;
        this.nameAdmin = nameAdmin;
        this.promoContestResponse = promoContestResponse;
        this.statusContest = statusContest;
        this.idAcr = idAcr;
        this.audioTitle = audioTitle;
        this.audio = audio;
        this.releaseDate = releaseDate;
        this.dataType = dataType;
        this.bucketName = bucketName;
        this.idBroadcaster = idBroadcaster;
        this.nameBroadcaster = nameBroadcaster;
        this.containAudio = containAudio;
    }
}
