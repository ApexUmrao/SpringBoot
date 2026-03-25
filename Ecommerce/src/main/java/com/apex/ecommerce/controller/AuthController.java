package com.apex.ecommerce.controller;

import com.apex.ecommerce.config.AppConstant;
import com.apex.ecommerce.model.AppRole;
import com.apex.ecommerce.model.Role;
import com.apex.ecommerce.model.User;
import com.apex.ecommerce.payload.AuthenticationResult;
import com.apex.ecommerce.repositories.RoleRepo;
import com.apex.ecommerce.repositories.UserRepo;
import com.apex.ecommerce.security.jwt.JwtUtils;
import com.apex.ecommerce.security.request.LoginRequest;
import com.apex.ecommerce.security.request.SignupRequest;
import com.apex.ecommerce.security.response.MessageResponse;
import com.apex.ecommerce.service.AuthService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	 @Autowired
	 AuthService authService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        AuthenticationResult result = authService.login(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                result.getJwtCookie().toString())
                .body(result.getResponse());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.register(signUpRequest);
    }

    @GetMapping("/username")
    public String gurrentUsername(Authentication authentication) {
        if (authentication != null) {
            return authentication.getName();
        }else{
            return "No User exists";
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails(Authentication authentication){
        return ResponseEntity.ok().body(authService.getCurrentUserDetails(authentication));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signoutUser(){
        ResponseCookie cookie = authService.logoutUser();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,
                        cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @GetMapping("/sellers")
    public ResponseEntity<?> getAllSellers(
            @RequestParam(name = "pageNumber", defaultValue = AppConstant.PAGE_NO, required = false) Integer pageNumber) {

        Sort sortByAndOrder = Sort.by(AppConstant.SORT_USERS_BY).descending();
        Pageable pageDetails = PageRequest.of(pageNumber ,
                Integer.parseInt(AppConstant.PAGE_SIZE), sortByAndOrder);
        return ResponseEntity.ok(authService.getAllSellers(pageDetails));
    }

}
