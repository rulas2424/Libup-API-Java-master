package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersRequest;
import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersShopRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Winners.WinnerListResponse;
import org.springframework.http.ResponseEntity;

public interface WinnersBusiness {
    ResponseEntity<WinnerListResponse> findAllWinnersByIdBroadcaster(WinnersRequest winnersRequest);

    ResponseEntity<WinnerListResponse> findHistoryWinnerByUserId(String userId);

    ResponseEntity<WinnerListResponse> getAllWinnersByIdShop(WinnersShopRequest winnersShopRequest);

    ResponseEntity<HeaderResponse> claimAward(String idWinner);
}
