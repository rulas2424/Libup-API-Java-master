package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersShopRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.WinnersDirect.WinnerListDirectResponse;
import org.springframework.http.ResponseEntity;

public interface WinnersDirectBusiness {
    ResponseEntity<WinnerListDirectResponse> findHistoryWinnerDirectByUserId(String userId);

    ResponseEntity<WinnerListDirectResponse> getAllWinnersDirectByIdShop(WinnersShopRequest winnersShopRequest);

    ResponseEntity<HeaderResponse> claimAwardDirect(String idWinner);
}
