package com.apex.socialMedia.repositories;

import com.apex.socialMedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {

}
