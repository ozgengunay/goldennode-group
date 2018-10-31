package com.thingabled.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.ThingContext;

public interface ThingContextRepository extends JpaRepository<ThingContext, String> {
	public ThingContext findByIdAndStatus(String id, Status status);

	public ThingContext findByIdAndUserIdAndStatus(String thingContextId, String userId, Status enabled);
 
}
