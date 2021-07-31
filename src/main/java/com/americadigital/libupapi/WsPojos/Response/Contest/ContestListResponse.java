package com.americadigital.libupapi.WsPojos.Response.Contest;

import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class ContestListResponse {
    public HeaderGeneric header;
    public List<ContestResponse> data;
    public Integer totalPages;
    public Long totalElements;

    public ContestListResponse(HeaderGeneric header, List<ContestResponse> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
