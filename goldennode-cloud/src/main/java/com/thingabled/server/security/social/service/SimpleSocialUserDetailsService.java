package com.thingabled.server.security.social.service;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;

import com.thingabled.commons.entity.Authorities;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.AuthorityRepository;
import com.thingabled.commons.repository.UserRepository;
import com.thingabled.server.security.ThingabledUserDetails;

@Service
public class SimpleSocialUserDetailsService implements SocialUserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSocialUserDetailsService.class);

    @Autowired
    private UserRepository repository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        LOGGER.debug("Loading user by user id: {}", userId);


        Users user = repository.findByEmail(userId);
        LOGGER.debug("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + userId);
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
        

        LOGGER.debug("Found user details: {}", principal);

        return principal;
    }
}
