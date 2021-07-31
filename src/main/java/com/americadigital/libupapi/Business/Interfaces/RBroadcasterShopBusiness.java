package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Promo.AddOrRemoveRelationRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.AddRelationBroadcasterShopRequest;
import com.americadigital.libupapi.WsPojos.Request.RBroadcasterShop.GetRelationsMatchRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.RBroadcasterShop.GetAllRBroadcasterShopResponse;
import org.springframework.http.ResponseEntity;

public interface RBroadcasterShopBusiness {
    //se tiene que ir agregando uno por uno
    ResponseEntity<HeaderResponse> addBroadcasterToShop(AddRelationBroadcasterShopRequest request);

    ResponseEntity<HeaderResponse> deleteRelationBroadcasterShop(String idRelationBroadcaster);

    ResponseEntity<GetAllRBroadcasterShopResponse> getAllRelationsBroadcasterShop(String idShop);

    ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsBroadcasterShopsActives(String idShop);

    ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsByIdBroadcaster(GetRelationsMatchRequest request);

    ResponseEntity<GetAllRBroadcasterShopResponse> getRelationsActivesByIdBroadcaster(String idBroadcaster);

    ResponseEntity<HeaderResponse> addOrRemoveRelation(AddOrRemoveRelationRequest request);
}
