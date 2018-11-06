package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.ThingOwnership;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface ThingOwnershipRepository extends JpaRepository<ThingOwnership, String> {


	public ThingOwnership findByThingIdAndUserIdAndStatus(String thingId, String userId, Status enabled);
	public ThingOwnership findByThingIdAndStatus(String thingId,Status enabled);
	public List<ThingOwnership> findByUserIdAndStatus(String userId,Status enabled);

	

	
	
	
	
	
}
