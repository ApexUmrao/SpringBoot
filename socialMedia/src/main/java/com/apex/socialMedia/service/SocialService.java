package com.apex.socialMedia.service;

import com.apex.socialMedia.model.SocialUser;
import com.apex.socialMedia.repositories.SocialUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public void deleteUser(Long id) {
		socialUserRepo.findById(id).orElseThrow(() 
					-> new RuntimeException("User not found with id: " + id));
		socialUserRepo.deleteById(id);		
	}

	public void updateUser(Long id, SocialUser socialUser) {
		SocialUser existingUser = socialUserRepo.findById(id).orElseThrow(() 
					-> new RuntimeException("User not found with id: " + id));
		existingUser.setSocialProfile(socialUser.getSocialProfile());
		existingUser.setPosts(socialUser.getPosts());
		existingUser.setGroups(socialUser.getGroups());
		socialUserRepo.save(existingUser);
	}

	
	

}
