package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorAwardsRequest;
import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorRequest;
import com.americadigital.libupapi.WsPojos.Request.Promo.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetAllPromosResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromoResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromosAppResponse;
import org.springframework.http.ResponseEntity;

public interface PromoBusiness {
    ResponseEntity<HeaderResponse> addPromo(AddPromoRequest addPromoRequest);

    ResponseEntity<HeaderResponse> updatePromo(UpdatePromoRequest updatePromoRequest);

    ResponseEntity<HeaderResponse> changeStatusPromo(ChangeStatusPromo changeStatusPromo);

    ResponseEntity<HeaderResponse> deletePromo(String idPromo);

    ResponseEntity<GetAllPromosResponse> filterPromosForIdShop(AllPromosShopRequest allPromosShopRequest);

    ResponseEntity<GetAllPromosResponse> getAwardsDirectosForIdShop(AllAwardsRequest allAwardsRequest);

    ResponseEntity<GetAllPromosResponse> getAwardsAudioForIdShop(AllAwardsRequest allAwardsRequest);

    ResponseEntity<GetPromosAppResponse> getPromosActivesByIdCategory(GetPromoByCategoryRequest request);

    ResponseEntity<GetPromosAppResponse> searchPromosOrDiscounts(SearchPromosRequest request);

    ResponseEntity<HeaderResponse> notificatorAwardWinnerDirect(NotificatorAwardsRequest request);

    ResponseEntity<HeaderResponse> sendNotificatorUsers(NotificatorRequest notificatorRequest);

    ResponseEntity<GetAllPromosResponse> getPromotionsOrDiscountsByIdShop(AllPromosRequest allPromosRequest);

    ResponseEntity<GetPromoResponse> getPromoOrDiscountById(String idPromo);

    ResponseEntity<GetAllPromosResponse> getAllPromosOrdDiscounts(String idShop);
}
