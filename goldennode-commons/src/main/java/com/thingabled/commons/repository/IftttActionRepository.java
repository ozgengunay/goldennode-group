package com.thingabled.commons.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.thingabled.commons.entity.IftttAction;

public interface IftttActionRepository extends JpaRepository<IftttAction, String> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<IftttAction> findByNameAndUseridAndProcessed(String name, String userid, int processed);

}
