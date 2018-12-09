package com.goldennode.server.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goldennode.server.entity.Authorities;

public interface AuthorityRepository extends JpaRepository<Authorities, String> {

    public Set<Authorities> findByUserId(String userId);
   
}
