package com.apex.socialMedia.repositories;

import com.apex.socialMedia.model.SocialProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialProfileRepo extends JpaRepository<SocialProfile, Long> {

}
