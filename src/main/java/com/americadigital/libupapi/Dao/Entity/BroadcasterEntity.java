package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_broadcaster")
@Data
public class BroadcasterEntity {

    @Id
    public String idBroadcaster;

    @Column(name = "name")
    public String name;

    @Column(name = "active")
    public boolean active;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "image_path")
    public String imagePath;


}
