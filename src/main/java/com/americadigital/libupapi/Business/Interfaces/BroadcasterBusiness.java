package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Broadcaster.AddBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Request.Broadcaster.StatusBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Request.Broadcaster.UpdateBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.AllBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.BroadcasterListResponse;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.BroadcasterResponse;
import com.americadigital.libupapi.WsPojos.Response.Broadcaster.SearchBroadcasterRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;

public interface BroadcasterBusiness {

    ResponseEntity<HeaderResponse> addBroadcaster(AddBroadcasterRequest request);

    ResponseEntity<HeaderResponse> updateBroadcaster(UpdateBroadcasterRequest request);

    ResponseEntity<HeaderResponse> changeStatus(StatusBroadcasterRequest request);

    ResponseEntity<BroadcasterResponse> getBroadcasterById(String idBroadcaster);

    ResponseEntity<BroadcasterListResponse> getBroadcasterListResponse(AllBroadcasterRequest request);

    ResponseEntity<BroadcasterListResponse> getBroadcasterListActives();

    ResponseEntity<BroadcasterListResponse> searchBroadcaster(SearchBroadcasterRequest request);

}
