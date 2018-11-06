package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Location;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface LocationRepository extends JpaRepository<Location, String> {

	
	List<Location> findByUserIdAndStatus(String userId,Status status);

	Location findByIdAndUserIdAndStatus(String id, String userId, Status status);

    
}
