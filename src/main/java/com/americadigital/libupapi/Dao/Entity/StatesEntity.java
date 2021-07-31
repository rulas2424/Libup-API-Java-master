package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_states")
public class StatesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long idState;

    @Column(name = "state")
    public String state;
}
