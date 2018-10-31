package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.SocialGroup;

public interface SocialGroupRepository extends JpaRepository<SocialGroup, String> {

	List<SocialGroup> findByUserIdAndStatus(String userId, Status status);

	SocialGroup findByIdAndUserIdAndStatus(String id, String userId, Status status);

}
