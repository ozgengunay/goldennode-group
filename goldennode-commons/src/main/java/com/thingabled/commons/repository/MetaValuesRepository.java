package com.thingabled.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.MetaValue;

public interface MetaValuesRepository extends JpaRepository<MetaValue, String> {

	MetaValue findByIdAndStatus(String metaValueId, Status enabled);

	List<MetaValue> findByStatus(Status enabled);

    
}
