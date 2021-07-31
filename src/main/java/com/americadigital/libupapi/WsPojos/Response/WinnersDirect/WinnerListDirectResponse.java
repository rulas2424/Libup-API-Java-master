package com.americadigital.libupapi.WsPojos.Response.WinnersDirect;

import com.americadigital.libupapi.Pojos.WinnersDirect.WinnersDirect;
import com.americadigital.libupapi.Utils.HeaderGeneric;

import java.util.List;

public class WinnerListDirectResponse {
    public HeaderGeneric header;
    public List<WinnersDirect> data;
    public Integer totalPages;
    public Long totalElements;

    public WinnerListDirectResponse(HeaderGeneric header, List<WinnersDirect> data, Integer totalPages, Long totalElements) {
        this.header = header;
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}
