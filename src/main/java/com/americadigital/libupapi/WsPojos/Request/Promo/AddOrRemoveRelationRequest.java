package com.americadigital.libupapi.WsPojos.Request.Promo;

import lombok.Data;

@Data
public class AddOrRemoveRelationRequest {
    public String idRelation;
    public boolean active;
}
