package com.americadigital.libupapi.Pojos.User;

public class Token {
    public String email;
    public String jwt;

    public Token(String email, String jwt) {
        this.email = email;
        this.jwt = jwt;
    }
}
