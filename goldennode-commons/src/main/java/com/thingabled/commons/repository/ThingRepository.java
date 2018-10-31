package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Thing;

public interface ThingRepository extends JpaRepository<Thing, String> {

	public Thing findByIdAndStatus(String id, Status status);

	public Thing findByPublickeyAndStatus(String publicKey, Status status);
	
	public List<Thing> findByThingContextIdAndStatus(String thingContextId, Status status);
}
