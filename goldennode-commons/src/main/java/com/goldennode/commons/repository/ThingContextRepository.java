package com.goldennode.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.ThingContext;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface ThingContextRepository extends JpaRepository<ThingContext, String> {
	public ThingContext findByIdAndStatus(String id, Status status);

	public ThingContext findByIdAndUserIdAndStatus(String thingContextId, String userId, Status enabled);
 
}
