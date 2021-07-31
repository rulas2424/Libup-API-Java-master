package com.americadigital.libupapi.Dao.Entity.OneToOne;

import com.americadigital.libupapi.Dao.Entity.PromoEntity;
import com.americadigital.libupapi.Dao.Entity.TContestEntity;
import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "t_contest")
@Data
public class TContestOneEntity {
    @Id
    public String idContest;

    @Column(name = "id_admin")
    @NotEmpty(message = "El id de admin es requerido.")
    public String idAdmin;

    @Column(name = "id_promo")
    @NotEmpty(message = "El id de promoción es requerido.")
    public String idPromo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public TContestEntity.StatusContest statusContest;

    @Column(name = "id_acr")
    @Size(min = 1, max = 50, message = "El id acr debe tener entre 1 y 50 caracteres.")
    @NotEmpty(message = "El id acr cloud es requerido.")
    public String idAcr;

    @Column(name = "audio_title")
    @Size(min = 1, max = 400, message = "El nombre del audio debe tener entre 1 y 400 caracteres.")
    @NotEmpty(message = "El nombre del audio es requerido.")
    public String audioTitle;

    @Column(name = "audio")
    @Size(min = 1, max = 60, message = "El audio file debe tener entre 1 y 60 caracteres.")
    @NotEmpty(message = "El audio file es requerido.")
    public String audio;

    @Column(name = "release_date")
    public Date releaseDate;

    @Column(name = "data_type")
    @Size(min = 1, max = 100, message = "El data type debe tener entre 1 y 100 caracteres.")
    @NotEmpty(message = "El dataType es requerido.")
    public String dataType;

    @Column(name = "bucket_name")
    @Size(min = 1, max = 400, message = "El nombre del bucket debe tener entre 1 y 400 caracteres.")
    @NotEmpty(message = "El nombre del bucket es requerido.")
    public String bucketName;

    @Column(name = "audio_id")
    @Size(min = 1, max = 100, message = "El id de audio debe tener entre 1 y 100 caracteres.")
    @NotEmpty(message = "El id de audio es requerido.")
    public String audioId;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_admin", insertable = false, updatable = false)
    @JsonIgnore
    public UserAdminEntity userAdminEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_promo", insertable = false, updatable = false)
    @JsonIgnore
    public PromoEntity promoEntity;

    public TContestOneEntity(){}
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

    public TContestEntity.StatusContest getStatusContest() {
        return statusContest;
    }

    public void setStatusContest(TContestEntity.StatusContest statusContest) {
        this.statusContest = statusContest;
    }

    public String getIdAcr() {
        return idAcr;
    }

    public void setIdAcr(String idAcr) {
        this.idAcr = idAcr;
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

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
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

    public String getAudioId() {
        return audioId;
    }

    public void setAudioId(String audioId) {
        this.audioId = audioId;
    }

    public UserAdminEntity getUserAdminEntity() {
        return userAdminEntity;
    }

    public void setUserAdminEntity(UserAdminEntity userAdminEntity) {
        this.userAdminEntity = userAdminEntity;
    }

    public PromoEntity getPromoEntity() {
        return promoEntity;
    }

    public void setPromoEntity(PromoEntity promoEntity) {
        this.promoEntity = promoEntity;
    }
}
