package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.SocialGroup;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface SocialGroupRepository extends JpaRepository<SocialGroup, String> {

	List<SocialGroup> findByUserIdAndStatus(String userId, Status status);

	SocialGroup findByIdAndUserIdAndStatus(String id, String userId, Status status);

}
