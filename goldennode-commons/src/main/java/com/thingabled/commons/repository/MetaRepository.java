package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Meta;

public interface MetaRepository extends JpaRepository<Meta, String> {

	Meta findByIdAndStatus(String id, Status status);
	List<Meta> findByStatus(Status status);
}
