package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.ThingOwnership;

public interface ThingOwnershipRepository extends JpaRepository<ThingOwnership, String> {


	public ThingOwnership findByThingIdAndUserIdAndStatus(String thingId, String userId, Status enabled);
	public ThingOwnership findByThingIdAndStatus(String thingId,Status enabled);
	public List<ThingOwnership> findByUserIdAndStatus(String userId,Status enabled);

	

	
	
	
	
	
}
