package com.americadigital.libupapi.WsPojos.Request.User;

import com.americadigital.libupapi.Dao.Entity.UserEntity;
import lombok.Data;

import java.util.Optional;

public class UserRegisterRequest {
    public String name;
    public String lastName;
    public String email;
    public Optional<String> password;
    public String tokenDevice;
    public Optional<String> phoneNumber;
    public Optional<String> socialId;
    public UserEntity.AccountType accountType;
    public Optional<String> profilePicture;
    public UserEntity.OperativeSystem  operativeSystem;
    public Long idState;

    public Long getIdState() {
        return idState;
    }

    public void setIdState(Long idState) {
        this.idState = idState;
    }

    public UserEntity.OperativeSystem getOperativeSystem() {
        return operativeSystem;
    }

    public void setOperativeSystem(UserEntity.OperativeSystem operativeSystem) {
        this.operativeSystem = operativeSystem;
    }

    public Optional<String> getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(Optional<String> profilePicture) {
        this.profilePicture = profilePicture;
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

    public Optional<String> getPassword() {
        return password;
    }

    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    public String getTokenDevice() {
        return tokenDevice;
    }

    public void setTokenDevice(String tokenDevice) {
        this.tokenDevice = tokenDevice;
    }

    public Optional<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Optional<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Optional<String> getSocialId() {
        return socialId;
    }

    public void setSocialId(Optional<String> socialId) {
        this.socialId = socialId;
    }

    public UserEntity.AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(UserEntity.AccountType accountType) {
        this.accountType = accountType;
    }
}
