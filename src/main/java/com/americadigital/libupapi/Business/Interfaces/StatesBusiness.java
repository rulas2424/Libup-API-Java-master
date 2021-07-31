package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Response.States.StatesResponse;
import org.springframework.http.ResponseEntity;


public interface StatesBusiness {
    ResponseEntity<StatesResponse> getAllStates();
}
