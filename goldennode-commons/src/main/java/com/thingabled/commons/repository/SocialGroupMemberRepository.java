package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.SocialGroupMember;

public interface SocialGroupMemberRepository extends JpaRepository<SocialGroupMember, String> {

	List<SocialGroupMember> findBySocialGroupIdAndStatus(String socialGroupId,Status status);

	SocialGroupMember findByIdAndStatus(String id, Status status);
    
}
