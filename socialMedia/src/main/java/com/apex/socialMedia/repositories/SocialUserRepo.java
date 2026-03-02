package com.apex.socialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apex.socialMedia.model.SocialUser;

public interface SocialUserRepo extends JpaRepository<SocialUser, Long> {

}
