package com.apex.socialMedia.repositories;

import com.apex.socialMedia.model.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialUserRepo extends JpaRepository<SocialUser, Long> {

}
