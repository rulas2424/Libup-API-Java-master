package com.americadigital.libupapi.Pojos.UserAdmin;

import lombok.Data;

@Data
public class ChangePasswordAdminRequest {
    public String idAdmin;
    public String currentPassword;
    public String newPassword;
}
