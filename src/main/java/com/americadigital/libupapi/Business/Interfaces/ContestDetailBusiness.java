package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.ContestDetail.ContestDetailRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;

public interface ContestDetailBusiness {
    ResponseEntity<HeaderResponse> addContestDetail(ContestDetailRequest contestDetailRequest);
}
