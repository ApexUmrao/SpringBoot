package com.apex.socialMedia.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apex.socialMedia.model.SocialProfile;

public interface SocialProfileRepo extends JpaRepository<SocialProfile, Long> {

}
