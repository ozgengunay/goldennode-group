package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.ThingPoint;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface ThingPointRepository extends JpaRepository<ThingPoint, String> {

	public List<ThingPoint> findByThingIdAndStatus(String thingId, Status enabled);
	public ThingPoint findByThingIdAndInternalIdAndStatus(String thingId,String internalId,Status enabled);
	
	
	
	public ThingPoint findByIdAndStatus(String id, Status status);
	
				      
	public List<ThingPoint> findByThingIdAndStatusOrderByCreationTimeDesc(String thingId, 
			Status enabled);
  
}
