package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.IftttTrigger;

public interface IftttTriggerRepository extends JpaRepository<IftttTrigger, String> {

	List<IftttTrigger> findTop50ByNameAndUseridOrderByTimestampDesc(String name, String userid);
	
	
}
