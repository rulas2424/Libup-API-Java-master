package com.americadigital.libupapi.Pojos.Winners;

import com.americadigital.libupapi.Dao.Entity.UserEntity;

public class UserWinner {
    public String idUser;
    public String name;
    public String lastName;
    public String email;
    public String profilePhoto;
    public String phoneNumber;
    public UserEntity.AccountType accountType;

    public UserWinner(String idUser, String name, String lastName, String email, String profilePhoto, String phoneNumber, UserEntity.AccountType accountType) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.profilePhoto = profilePhoto;
        this.phoneNumber = phoneNumber;
        this.accountType = accountType;
    }
}
