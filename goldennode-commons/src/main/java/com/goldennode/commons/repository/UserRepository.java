package com.goldennode.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.entity.BaseEntity.Status;

public interface UserRepository extends JpaRepository<Users, String> {

    public Users findByEmail(String email);
    public Users findById(String id);
}
