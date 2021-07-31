package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.Pojos.UserAdmin.ChangePasswordAdminRequest;
import com.americadigital.libupapi.WsPojos.Request.User.AllUserRequest;
import com.americadigital.libupapi.WsPojos.Request.User.ChangeStatusUserRequest;
import com.americadigital.libupapi.WsPojos.Request.User.SearchRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.ChangePassRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminLogginRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminRegisterRequest;
import com.americadigital.libupapi.WsPojos.Request.UserAdmin.UserAdminUpdateRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.User.UserRegisterResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.GetAllUserAdminResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.GetUserAdminByIdResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.PlanActiveResponse;
import com.americadigital.libupapi.WsPojos.Response.UserAdmin.UserLoginAdminResponse;
import org.springframework.http.ResponseEntity;

public interface UserAdminBusiness {
    ResponseEntity<UserLoginAdminResponse> loginUserAdmin(UserAdminLogginRequest loginRequest);

    ResponseEntity<UserRegisterResponse> registerUser(UserAdminRegisterRequest adminRegisterRequest);

    ResponseEntity<GetAllUserAdminResponse> getAllUsersAdmin(AllUserRequest userRequest);

    ResponseEntity<GetAllUserAdminResponse> searchUsersAdmin(SearchRequest searchRequest);

    ResponseEntity<GetUserAdminByIdResponse> getUserById(String idUser);

    ResponseEntity<UserRegisterResponse> updateUserAdmin(UserAdminUpdateRequest updateRequest);

    ResponseEntity<HeaderResponse> changeStatus(ChangeStatusUserRequest request);

    ResponseEntity<HeaderResponse> deleteUser(String idUser);

    ResponseEntity<HeaderResponse> changePassword(ChangePasswordAdminRequest request);

    ResponseEntity<HeaderResponse> changePasswordAdmin(ChangePassRequest request);

    ResponseEntity<PlanActiveResponse> verifyPlanActive(String idCommerce);
}
