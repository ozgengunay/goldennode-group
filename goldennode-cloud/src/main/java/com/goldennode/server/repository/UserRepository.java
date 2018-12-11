package com.goldennode.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goldennode.server.entity.Users;

public interface UserRepository extends JpaRepository<Users, String> {

    public Users findByEmail(String email);
    
    public Users findByUsername(String username);

    public Users findByApiKey(String apiKey);
    

}
