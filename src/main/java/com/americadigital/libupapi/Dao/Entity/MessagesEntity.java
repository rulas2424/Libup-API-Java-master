package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "t_messages")
public class MessagesEntity {

    @Id
    public String idMessage;

    @Column(name = "message")
    public String message;

    @Column(name = "date_hour")
    public Date dateHour;

    @Column(name = "type_user")
    @Enumerated(value = EnumType.STRING)
    public TypeUser typeUser;

    @Column(name = "active")
    public boolean active;

    @Column(name = "msg_type")
    @Enumerated(value = EnumType.STRING)
    public MsgType msgType;

    @Column(name = "path_file")
    public String pathFile;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "id_channel")
    public String idChannel;

    @Column(name = "id_admin")
    public String idAdmin;

    @Column(name = "id_user")
    public String idUser;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_admin", insertable = false, updatable = false)
    @JsonIgnore
    public UserAdminEntity userAdminEntity;

    @OneToOne(optional = false)
    @JoinColumn(name = "id_user", insertable = false, updatable = false)
    @JsonIgnore
    public UserEntity userEntity;

    public enum MsgType {
        Imagen, Video, Texto
    }

    public enum TypeUser {
        App,
        Panel
    }
}
