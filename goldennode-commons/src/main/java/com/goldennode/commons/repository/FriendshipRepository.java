package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {

	List<Friendship> findByUserId(String userId);

	Friendship findByIdAndUserId(String id, String userId);

    
}
