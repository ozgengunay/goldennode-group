package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.SocialGroupContext;
import com.goldennode.commons.entity.SocialGroupMember;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface SocialGroupContextRepository extends JpaRepository<SocialGroupContext, String> {

	List<SocialGroupContext> findBySocialGroupIdAndStatus(String socialGroupId,Status status);

	SocialGroupContext findByIdAndStatus(String id, Status status);
    
}
