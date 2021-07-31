package com.americadigital.libupapi.WsPojos.Response.User;

import com.americadigital.libupapi.Pojos.User.UserAll;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class GetAllUserResponse {
    public HeaderGeneric header;
    public List<UserAll> data;
    public Integer totalPages;
    public Long totalElements;

    public GetAllUserResponse(HeaderGeneric header, List<UserAll> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
