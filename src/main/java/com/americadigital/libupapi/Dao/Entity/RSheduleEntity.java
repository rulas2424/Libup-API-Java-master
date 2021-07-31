package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "r_schedule")
@Data
public class RSheduleEntity {

    @Id
    public String id_schedule;

    @Column(name = "day")
    @Enumerated(value = EnumType.STRING)
    public WeekDay day;

    @Column(name = "hour_open")
    @Size(min = 1, max = 20, message = "Hora de apertura debe tener entre 1 y 20 caracteres.")
    public String hourOpen;

    @Column(name = "hour_close")
    @Size(min = 1, max = 20, message = "Hora de cierre debe tener entre 1 y 20 caracteres.")
    public String hourClose;

    @Column(name = "is_closed")
    public boolean isClosed;

    @Column(name = "id_branch")
    @NotEmpty(message = "El id de sucursal es requerido.")
    public String idBranch;

    public RSheduleEntity() {

    }

    public enum WeekDay {
        Lunes,
        Martes,
        Miercoles,
        Jueves,
        Viernes,
        Sabado,
        Domingo
    }


    public RSheduleEntity(String id_schedule, WeekDay day,
                          String hourOpen, @Size(min = 1, max = 20, message = "Hora de cierre debe tener entre 1 y 20 caracteres.") String hourClose, boolean isClosed, @Size(min = 1, max = 20, message = "El id branch debe tener entre 1 y 20 caracteres.") @NotEmpty(message = "El id de sucursal es requerido.") String idBranch) {
        this.id_schedule = id_schedule;
        this.day = day;
        this.hourOpen = hourOpen;
        this.hourClose = hourClose;
        this.isClosed = isClosed;
        this.idBranch = idBranch;
    }
}
