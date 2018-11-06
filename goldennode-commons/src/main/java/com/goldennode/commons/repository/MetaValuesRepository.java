package com.goldennode.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.MetaValue;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface MetaValuesRepository extends JpaRepository<MetaValue, String> {

	MetaValue findByIdAndStatus(String metaValueId, Status enabled);

	List<MetaValue> findByStatus(Status enabled);

    
}
