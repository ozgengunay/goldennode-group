package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.Friendship;

public interface FriendshipRepository extends JpaRepository<Friendship, String> {

	List<Friendship> findByUserId(String userId);

	Friendship findByIdAndUserId(String id, String userId);

    
}
