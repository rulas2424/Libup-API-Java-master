package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.Pojos.Shop.ActivePojo;
import com.americadigital.libupapi.WsPojos.Request.Shop.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.AllShopResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.CommerceResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopGetAllResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopResponse;
import org.springframework.http.ResponseEntity;

public interface ShopBusiness {
    ResponseEntity<CommerceResponse> addCommerce(AddShopRequest shopRequest);

    ResponseEntity<CommerceResponse> updateCommerce(UpdateShopRequest updateShopRequest);

    ResponseEntity<CommerceResponse> changeActiveStatus(ActivePojo activePojo);

    ResponseEntity<ShopResponse> getCommerceById(String id_shop);

    ResponseEntity<ShopGetAllResponse> getAllShopsPanel(AllShopRequest shopRequest);

    ResponseEntity<ShopGetAllResponse> searchCommercesPanel(SearchShopRequest searchShopRequest);

    ResponseEntity<AllShopResponse> getAllShopsActives(Long idState);

    ResponseEntity<AllShopResponse> getAllShops();

    ResponseEntity<HeaderResponse> deleteCommerce(String id_shop);

    ResponseEntity<HeaderResponse> addCommercesFromPage(AddCommercesRequest request);
}
