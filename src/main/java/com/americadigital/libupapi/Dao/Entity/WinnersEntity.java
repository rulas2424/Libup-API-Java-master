package com.americadigital.libupapi.Dao.Entity;

import com.americadigital.libupapi.Dao.Entity.OneToOne.TContestOneEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_winner")
public class WinnersEntity {
    @Id
    public String idWinner;

    @Column(name = "id_contest")
    public String idContest;

    @Column(name = "id_user")
    public String idUser;

    @Column(name = "tick_count")
    public int tickCount;

    @Column(name = "date")
    public Date winningDate;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public StatusWinner statusWinner;

    @Column(name = "id_shop")
    public String idShop;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public TypeWinner typeWinner;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_contest", insertable = false, updatable = false)
    @JsonIgnore
    public TContestOneEntity tContestEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    public UserEntity userEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_broadcaster", insertable = false, updatable = false)
    @JsonIgnore
    public BroadcasterEntity broadcasterEntity;

    public WinnersEntity() {
    }

    public enum StatusWinner {
        Ganado, Reclamado
    }

    public enum TypeWinner {
        ConAudio, SinAudio
    }

}
