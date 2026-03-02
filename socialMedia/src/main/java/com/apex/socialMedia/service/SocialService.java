package com.apex.socialMedia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apex.socialMedia.model.SocialUser;
import com.apex.socialMedia.repositories.SocialUserRepo;

@Service
public class SocialService {
	
	@Autowired
	private SocialUserRepo socialUserRepo;
	
	public List<SocialUser> getAllUsers() {
		return socialUserRepo.findAll();
	}

	public SocialUser saveUser(SocialUser socialUser) {
		return socialUserRepo.save(socialUser);
	}

	
	

}
