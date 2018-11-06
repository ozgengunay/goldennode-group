package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Thing;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface ThingRepository extends JpaRepository<Thing, String> {

	public Thing findByIdAndStatus(String id, Status status);

	public Thing findByPublickeyAndStatus(String publicKey, Status status);
	
	public List<Thing> findByThingContextIdAndStatus(String thingContextId, Status status);
}
