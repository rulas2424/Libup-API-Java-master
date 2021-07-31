package com.americadigital.libupapi.WsPojos.Request.Shop;

import lombok.Data;

import java.util.Optional;

@Data
public class AddCommercesRequest {
    public String commerce;
    public Optional<String> urlCommerce;
    public String name;
    public String lastName;
    public String email;
    public String password;
    public String phoneNumber;
}
