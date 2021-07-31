package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.Branch.AddBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.CoordsRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.StatusBranchRequest;
import com.americadigital.libupapi.WsPojos.Request.Branch.UpdateBranchRequest;
import com.americadigital.libupapi.WsPojos.Response.Branch.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;

public interface BranchBusiness {
    ResponseEntity<BranchResponse> addBranch(AddBranchRequest branchRequest);

    ResponseEntity<BranchResponse> updateBranch(UpdateBranchRequest branchRequest);

    ResponseEntity<BranchResponse> changeStatus(StatusBranchRequest branchRequest);

    ResponseEntity<BranchGetResponse> getBranchById(String idBranch);

    ResponseEntity<BranchListResponse> getAllBranchesForAdmin(String idShop);

    ResponseEntity<BranchActivesListResponse> getAllBranches(String idShop);

    ResponseEntity<HeaderResponse> deleteBranch(String idBranch);

    ResponseEntity<CoordenadasResponse> getNearestStore(CoordsRequest coordsRequest);
}
