package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.WsPojos.Request.User.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.User.*;
import org.springframework.http.ResponseEntity;

public interface UserBusiness {
    ResponseEntity<UserLoginResponse> loginUser(UserLogginRequest loginRequest);

    ResponseEntity<RefreshTokenResponse> refreshToken(RefreshRequest refreshRequest);

    ResponseEntity<UserRegisterResponse> registerUser(UserRegisterRequest registerRequest);

    ResponseEntity<GetAllUserResponse> getAllUsers(AllUserRequest userRequest);

    ResponseEntity<GetAllUserResponse> searchUsers(SearchRequest searchRequest);

    ResponseEntity<GetUserByIdResponse> getUserById(String idUser);

    ResponseEntity<UserRegisterResponse> updateUser(UserUpdateRequest updateRequest);

    ResponseEntity<HeaderResponse> changeStatus(ChangeStatusUserRequest request);

    ResponseEntity<HeaderResponse> deleteUser(String idUser);

    ResponseEntity<HeaderResponse> changePassword(ChangePasswordRequest request);

    ResponseEntity<HeaderResponse> updateCoordinates(UserCoordinatesRequest request);
}
