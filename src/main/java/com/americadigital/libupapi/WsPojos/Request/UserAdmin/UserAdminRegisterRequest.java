package com.americadigital.libupapi.WsPojos.Request.UserAdmin;

import com.americadigital.libupapi.Dao.Entity.UserAdminEntity;

import java.util.Optional;

public class UserAdminRegisterRequest {
    public String name;
    public String lastName;
    public String email;
    public String password;
    public Optional<String> profilePicture;
    public Optional<String> phoneNumber;
    public UserAdminEntity.TypeAdmin typeAdmin;
    public Optional<String> idShop;
    public Optional<String> idBroadcaster;

    public Optional<String> getIdBroadcaster() {
        return idBroadcaster;
    }

    public void setIdBroadcaster(Optional<String> idBroadcaster) {
        this.idBroadcaster = idBroadcaster;
    }

    public Optional<String> getIdShop() {
        return idShop;
    }

    public void setIdShop(Optional<String> idShop) {
        this.idShop = idShop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Optional<String> getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Optional<String> profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Optional<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserAdminEntity.TypeAdmin getTypeAdmin() {
        return typeAdmin;
    }

    public void setTypeAdmin(UserAdminEntity.TypeAdmin typeAdmin) {
        this.typeAdmin = typeAdmin;
    }
}
