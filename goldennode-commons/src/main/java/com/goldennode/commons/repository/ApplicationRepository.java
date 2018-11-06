package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Application;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface ApplicationRepository extends JpaRepository<Application, String> {


	List<Application> findByUserIdAndStatus(String userId, Status status);

	Application findByIdAndUserIdAndStatus(String id, String userId, Status status);

}
