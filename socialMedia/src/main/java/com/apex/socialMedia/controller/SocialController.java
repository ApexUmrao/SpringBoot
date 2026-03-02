package com.apex.socialMedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.apex.socialMedia.model.SocialUser;
import com.apex.socialMedia.service.SocialService;

@Controller
public class SocialController {
	
	@Autowired
	private SocialService socialService;
	
	@GetMapping("/social/users")
	public ResponseEntity<List<SocialUser>> getUsers(){
		return new ResponseEntity<>(socialService.getAllUsers(), HttpStatus.OK);
	}
	
	@PostMapping("/social/users")
	public ResponseEntity<SocialUser> saveUser(@RequestBody SocialUser socialUser){
		return new ResponseEntity<>(socialService.saveUser(socialUser), HttpStatus.CREATED);
	}

}
