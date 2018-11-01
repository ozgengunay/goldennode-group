package com.goldennode.server.security.social.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thingabled.commons.entity.Authorities;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.entity.BaseEntity.Status;
import com.thingabled.commons.repository.AuthorityRepository;
import com.thingabled.commons.repository.UserRepository;
import com.goldennode.server.security.ThingabledUserDetails;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUserDetailsService.class);

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by username: {}", username);

        Users user = repository.findByEmail(username);
        
        LOGGER.debug("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        Set<Authorities> authorities= authorityRepository.findByUsername(user.getEmail());
        Set<GrantedAuthority> rols = new HashSet<GrantedAuthority>();
		for (Authorities authority:authorities) {
			rols.add(new SimpleGrantedAuthority(authority.getAuthority()));
		}
       
        ThingabledUserDetails principal = new ThingabledUserDetails(user.getEmail(),user.getPassword_(),rols);
        principal.setFirstName(user.getFirstName());
        principal.setId(user.getId());
        principal.setLastName(user.getLastName());
        principal.setSocialSignInProvider(user.getSignInProvider());
        

        LOGGER.debug("Returning user details: {}", principal);

        return principal;
    }
}
