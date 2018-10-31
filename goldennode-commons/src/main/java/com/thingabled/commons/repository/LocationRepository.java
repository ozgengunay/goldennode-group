package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Location;

public interface LocationRepository extends JpaRepository<Location, String> {

	
	List<Location> findByUserIdAndStatus(String userId,Status status);

	Location findByIdAndUserIdAndStatus(String id, String userId, Status status);

    
}
