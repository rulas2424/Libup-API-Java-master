package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_channels")
public class ChannelsEntity {

    @Id
    public String idChannel;

    @Column(name = "tittle")
    public String tittle;

    @Column(name = "description")
    public String description;

    @Column(name = "url")
    public String url;

    @Column(name = "active")
    public boolean active;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "image")
    public String image;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_broadcaster", insertable = false, updatable = false)
    @JsonIgnore
    public BroadcasterEntity broadcasterEntity;
}
