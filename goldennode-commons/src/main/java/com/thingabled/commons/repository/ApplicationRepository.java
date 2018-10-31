package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.Application;
import com.thingabled.commons.entity.BaseEntity.Status;

public interface ApplicationRepository extends JpaRepository<Application, String> {


	List<Application> findByUserIdAndStatus(String userId, Status status);

	Application findByIdAndUserIdAndStatus(String id, String userId, Status status);

}
