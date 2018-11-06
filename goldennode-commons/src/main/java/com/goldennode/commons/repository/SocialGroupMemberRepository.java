package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.SocialGroupMember;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface SocialGroupMemberRepository extends JpaRepository<SocialGroupMember, String> {

	List<SocialGroupMember> findBySocialGroupIdAndStatus(String socialGroupId,Status status);

	SocialGroupMember findByIdAndStatus(String id, Status status);
    
}
