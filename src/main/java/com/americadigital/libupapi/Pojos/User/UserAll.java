package com.americadigital.libupapi.Pojos.User;

import com.americadigital.libupapi.Dao.Entity.UserEntity;

public class UserAll {
    public String idUser;
    public String name;
    public String lastName;
    public String email;
    public String phoneNumber;
    public Boolean active;
    public String profile;
    public UserEntity.AccountType accountType;

    public UserAll(String idUser, String name, String lastName, String email, String phoneNumber, Boolean active, String profile, UserEntity.AccountType accountType) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.active = active;
        this.profile = profile;
        this.accountType = accountType;
    }
}
