package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name = "t_winner_direct")
public class WinnerDirectEntity {
    @Id
    public String idWinner;

    @Column(name = "id_user")
    @NotEmpty(message = "El id de usuario es requerido.")
    public String idUser;

    @Column(name = "date")
    public Date winningDate;

    @Column(name = "id_promo")
    @NotEmpty(message = "El id de promoción es requerido.")
    public String idPromo;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    public WinnersEntity.StatusWinner statusWinner;

    @Column(name = "id_shop")
    public String idShop;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    public UserEntity userEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_promo", insertable = false, updatable = false)
    @JsonIgnore
    public PromoEntity promoEntity;

}
