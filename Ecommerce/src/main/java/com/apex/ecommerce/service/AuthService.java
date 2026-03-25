package com.apex.ecommerce.service;

import com.apex.ecommerce.payload.AuthenticationResult;
import com.apex.ecommerce.payload.UserResponse;
import com.apex.ecommerce.security.request.LoginRequest;
import com.apex.ecommerce.security.request.SignupRequest;
import com.apex.ecommerce.security.response.MessageResponse;
import com.apex.ecommerce.security.response.UserInfoResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface AuthService {

    AuthenticationResult login(LoginRequest loginRequest);

    ResponseEntity<MessageResponse> register(SignupRequest signUpRequest);

    UserInfoResponse getCurrentUserDetails(Authentication authentication);

    ResponseCookie logoutUser();

    UserResponse getAllSellers(Pageable pageable);
}
