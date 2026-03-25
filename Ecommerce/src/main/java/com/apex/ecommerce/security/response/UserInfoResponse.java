package com.apex.ecommerce.security.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoResponse {

    private Long id;
    private String jwtToken;

    private String username;
    private List<String> roles;
    
    private String email;

    public UserInfoResponse(Long id, String jwtToken, String username, List<String> roles, String email) {
        this.id = id;
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
        this.email = email;
    }

    public UserInfoResponse(Long id, String username, String email , List<String> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}


