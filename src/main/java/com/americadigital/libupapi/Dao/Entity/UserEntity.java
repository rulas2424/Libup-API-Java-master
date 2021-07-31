package com.americadigital.libupapi.Dao.Entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "t_user")
@Data
public class UserEntity {
    @Id
    public String idUser;

    @Column(name = "name")
    @Size(min = 1, max = 30, message
            = "Nombre debe tener entre 1 y 30 caracteres.")
    @NotEmpty(message = "El nombre es requerido.")
    public String name;

    @Column(name = "last_name")
    @Size(min = 1, max = 40, message
            = "Apellidos debe tener entre 1 y 40 caracteres.")
    @NotEmpty(message = "Los apellidos son requeridos.")
    public String lastName;

    @Column(name = "email")
    @Email(message = "El correo electrónico debe ser válido.")
    @NotEmpty(message = "El email es requerido.")
    @Size(min = 1, max = 255, message
            = "Email debe tener entre 1 y 255 caracteres.")
    public String email;

    @Column(name = "profile_photo")
    public String name_profile;

    @Column(name = "password")
    @Size(min = 32, max = 32, message
            = "Password ENCRIPT debe tener 32 caracteres.")
    public String password;

    @Column(name = "token_android")
    @Size(min = 1, max = 160, message
            = "Token android debe tener entre 1 y 160 caracteres.")
    public String tokenAndroid;


    @Column(name = "token_ios")
    @Size(min = 1, max = 160, message
            = "Token ios debe tener entre 1 y 160 caracteres.")
    public String tokenIos;

    @Column(name = "phone_number")
    @Size(min = 10, max = 10, message
            = "Teléfono debe tener 10 caracteres.")
    public String phoneNumber;

    @Column(name = "active")
    public boolean active;

    @Column(name = "social_id")
    @Size(min = 1, max = 100, message
            = "SocialID debe tener entre 1 y 100 caracteres.")
    public String socialId;

    @Column(name = "acount_type")
    @Enumerated(value = EnumType.STRING)
    public AccountType accountType;

    @Column(name = "id_state")
    public Long idState;

    @Column(name = "is_deleted")
    public boolean isDeleted;

    @Column(name = "latitude")
    public String latitude;

    @Column(name = "longitude")
    public String longitude;

    public enum AccountType {
        Normal,
        Facebook,
        Google,
        Twitter
    }

    public enum OperativeSystem {
        Android,
        Ios,
    }

    public UserEntity() {

    }
}

