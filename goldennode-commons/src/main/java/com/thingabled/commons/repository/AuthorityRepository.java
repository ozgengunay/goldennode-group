package com.thingabled.commons.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.thingabled.commons.entity.Authorities;

public interface AuthorityRepository extends JpaRepository<Authorities, String> {

    public Set<Authorities> findByUsername(String username);
   
}
