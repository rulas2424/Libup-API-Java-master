package com.americadigital.libupapi.Pojos.UserAdmin;

public class UserAdminRepo {
    public String idAdmin;
    public String name;
    public String lastName;
    public String email;
    public String phoneNumber;

    public UserAdminRepo(String idAdmin, String name, String lastName, String email, String phoneNumber) {
        this.idAdmin = idAdmin;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
