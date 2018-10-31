package com.thingabled.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.entity.Users;

public interface UserRepository extends JpaRepository<Users, String> {

    public Users findByEmail(String email);
    public Users findById(String id);
}
