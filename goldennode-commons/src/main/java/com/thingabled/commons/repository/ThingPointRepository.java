package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.ThingPoint;

public interface ThingPointRepository extends JpaRepository<ThingPoint, String> {

	public List<ThingPoint> findByThingIdAndStatus(String thingId, Status enabled);
	public ThingPoint findByThingIdAndInternalIdAndStatus(String thingId,String internalId,Status enabled);
	
	
	
	public ThingPoint findByIdAndStatus(String id, Status status);
	
				      
	public List<ThingPoint> findByThingIdAndStatusOrderByCreationTimeDesc(String thingId, 
			Status enabled);
  
}
