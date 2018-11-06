package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Meta;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface MetaRepository extends JpaRepository<Meta, String> {

	Meta findByIdAndStatus(String id, Status status);
	List<Meta> findByStatus(Status status);
}
