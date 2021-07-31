package com.americadigital.libupapi.WsPojos.Response.UserAdmin;

import com.americadigital.libupapi.Pojos.UserAdmin.UserAdminGet;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetAllUserAdminResponse {
    public HeaderGeneric header;
    public List<UserAdminGet> data;
    public Integer totalPages;
    public Long totalElements;

    public GetAllUserAdminResponse(HeaderGeneric header, List<UserAdminGet> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
