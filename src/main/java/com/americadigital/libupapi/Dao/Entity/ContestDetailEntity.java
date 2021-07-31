package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name = "t_contest_detail")
public class ContestDetailEntity {

    @Id
    public String idDetail;

    @Column(name = "id_contest")
    @NotEmpty(message = "El id de concurso es requerido.")
    public String idContest;

    @Column(name = "id_user")
    @NotEmpty(message = "El id de usuario es requerido.")
    public String idUser;

    @Column(name = "tick_count")
    public int tickCount;

    @Column(name = "release_date")
    public Date releaseDate;
}
