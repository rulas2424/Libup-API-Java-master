package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "t_contest")
@Data
public class TContestEntity {

    @Id
    public String idContest;

    @Column(name = "id_admin")
    public String idAdmin;

    @Column(name = "id_promo")
    public String idPromo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public StatusContest statusContest;

    @Column(name = "id_acr")
    public String idAcr;

    @Column(name = "audio_title")
    public String audioTitle;

    @Column(name = "audio")
    public String audio;

    @Column(name = "release_date")
    public Date releaseDate;

    @Column(name = "data_type")
    public String dataType;

    @Column(name = "bucket_name")
    public String bucketName;

    @Column(name = "audio_id")
    public String audioId;

    @Column(name = "contain_audio")
    public boolean containAudio;

    @Column(name = "id_shop")
    public String idShop;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_admin", insertable = false, updatable = false)
    @JsonIgnore
    public UserAdminEntity userAdminEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_broadcaster", insertable = false, updatable = false)
    @JsonIgnore
    public BroadcasterEntity broadcasterEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_promo", insertable = false, updatable = false)
    @JsonIgnore
    public PromoEntity promoEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_contest", insertable = false, updatable = false)
    @JsonIgnore
    public WinnersEntity winnersEntity;

    public enum StatusContest {
        Creado, EnCurso, Terminado
    }


    public BroadcasterEntity getBroadcasterEntity() {
        return broadcasterEntity;
    }

    public void setBroadcasterEntity(BroadcasterEntity broadcasterEntity) {
        this.broadcasterEntity = broadcasterEntity;
    }

    public String getIdBroadcaster() {
        return idBroadcaster;
    }

    public void setIdBroadcaster(String idBroadcaster) {
        this.idBroadcaster = idBroadcaster;
    }

    public boolean isContainAudio() {
        return containAudio;
    }

    public String getIdShop() {
        return idShop;
    }

    public void setIdShop(String idShop) {
        this.idShop = idShop;
    }

    public void setContainAudio(boolean containAudio) {
        this.containAudio = containAudio;
    }

    public WinnersEntity getWinnersEntity() {
        return winnersEntity;
    }

    public void setWinnersEntity(WinnersEntity winnersEntity) {
        this.winnersEntity = winnersEntity;
    }

    public PromoEntity getPromoEntity() {
        return promoEntity;
    }

    public void setPromoEntity(PromoEntity promoEntity) {
        this.promoEntity = promoEntity;
    }

    public UserAdminEntity getUserAdminEntity() {
        return userAdminEntity;
    }

    public void setUserAdminEntity(UserAdminEntity userAdminEntity) {
        this.userAdminEntity = userAdminEntity;
    }

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getAudioTitle() {
        return audioTitle;
    }

    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getIdContest() {
        return idContest;
    }

    public void setIdContest(String idContest) {
        this.idContest = idContest;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(String idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getIdPromo() {
        return idPromo;
    }

    public void setIdPromo(String idPromo) {
        this.idPromo = idPromo;
    }

    public StatusContest getStatusContest() {
        return statusContest;
    }

    public void setStatusContest(StatusContest statusContest) {
        this.statusContest = statusContest;
    }

    public String getIdAcr() {
        return idAcr;
    }

    public void setIdAcr(String idAcr) {
        this.idAcr = idAcr;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
