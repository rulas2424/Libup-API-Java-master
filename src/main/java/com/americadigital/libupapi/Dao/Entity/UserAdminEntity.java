package com.americadigital.libupapi.Dao.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_admin")
@Data
public class UserAdminEntity {

    @Id
    public String idAdmin;

    @Column(name = "name")
    @Size(min = 1, max = 30, message
            = "Nombre debe tener entre 1 y 30 caracteres.")
    @NotEmpty(message = "El nombre es requerido.")
    public String name;

    @Column(name = "last_name")
    @Size(min = 1, max = 40, message
            = "Apellidos debe tener entre 1 y 40 caracteres.")
    @NotEmpty(message = "Los Apellidos son requeridos.")
    public String lastName;

    @Column(name = "email")
    @Email(message = "El correo electrónico debe ser válido.")
    @NotEmpty(message = "El email es requerido.")
    @Size(min = 1, max = 255, message
            = "Email debe tener entre 1 y 255 caracteres.")
    public String email;

    @Column(name = "password")
    @NotEmpty(message = "El Password es requerido.")
    public String password;

    @Column(name = "profile_photo")
    public String profilePicture;

    @Column(name = "phone_number")
    @Size(min = 10, max = 10, message
            = "Teléfono debe tener 10 caracteres.")
    public String phoneNumber;

    @Column(name = "active")
    public boolean active;

    @Column(name = "type")
    @Enumerated(value = EnumType.STRING)
    public TypeAdmin typeAdmin;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "id_shop")
    public String idShop;

    @Column(name = "id_broadcaster")
    public String idBroadcaster;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    @JsonIgnore
    public ShopEntity shopEntity;

    @OneToOne(optional = true)
    @JoinColumn(name = "id_broadcaster", insertable = false, updatable = false)
    @JsonIgnore
    public BroadcasterEntity broadcasterEntity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_shop", insertable = false, updatable = false)
    public ShopEntity shopEntityBack;

    public enum TypeAdmin {
        SuperAdmin,
        Radio,
        Comercio
    }

}
