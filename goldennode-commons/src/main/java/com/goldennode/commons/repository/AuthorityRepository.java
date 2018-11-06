package com.goldennode.commons.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.goldennode.commons.entity.Authorities;

public interface AuthorityRepository extends JpaRepository<Authorities, String> {

    public Set<Authorities> findByUsername(String username);
   
}
