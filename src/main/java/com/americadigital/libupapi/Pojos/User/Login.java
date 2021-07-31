package com.americadigital.libupapi.Pojos.User;

public class Login {
    public String idUser;
    public String name;
    public String lastName;
    public String jwt;
    public Long idState;

    public Login(String idUser, String name, String lastName, String jwt, Long idState) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.jwt = jwt;
        this.idState = idState;
    }
}
