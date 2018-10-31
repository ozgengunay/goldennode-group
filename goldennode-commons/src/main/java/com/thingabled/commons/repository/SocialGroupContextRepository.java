package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.SocialGroupContext;
import com.thingabled.commons.entity.SocialGroupMember;

public interface SocialGroupContextRepository extends JpaRepository<SocialGroupContext, String> {

	List<SocialGroupContext> findBySocialGroupIdAndStatus(String socialGroupId,Status status);

	SocialGroupContext findByIdAndStatus(String id, Status status);
    
}
