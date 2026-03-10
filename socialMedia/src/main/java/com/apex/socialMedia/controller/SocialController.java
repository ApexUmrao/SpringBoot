package com.apex.socialMedia.controller;

import com.apex.socialMedia.model.SocialUser;
import com.apex.socialMedia.service.SocialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

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
	
	@DeleteMapping("/social/users/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		socialService.deleteUser(id);
		return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	}
	
	@PutMapping("/social/users/{id}")
	public ResponseEntity<SocialUser> updateUser(@PathVariable Long id, @RequestBody SocialUser socialUser){
		socialService.updateUser(id,socialUser);
		return new ResponseEntity<>(socialService.saveUser(socialUser), HttpStatus.OK);
	}
}
