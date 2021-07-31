package com.americadigital.libupapi.WsPojos.Response.Winners;

import com.americadigital.libupapi.Pojos.Winners.Winners;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class WinnerListResponse {
    public HeaderGeneric header;
    public List<Winners> data;
    public Integer totalPages;
    public Long totalElements;

    public WinnerListResponse(HeaderGeneric header, List<Winners> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
