package com.goldennode.server.security.web;

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
import com.goldennode.server.entity.Authorities;
import com.goldennode.server.entity.Users;
import com.goldennode.server.repository.AuthorityRepository;
import com.goldennode.server.repository.UserRepository;
import com.goldennode.server.security.GoldenNodeUser;

@Service
public class AccountUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by username: {}", username);
        Users user = userRepository.findByEmail(username);
        LOGGER.debug("Found user: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        Set<Authorities> authorities = authorityRepository.findByUserId(user.getId());
        Set<GrantedAuthority> rols = new HashSet<GrantedAuthority>();
        for (Authorities authority : authorities) {
            rols.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        GoldenNodeUser principal = new GoldenNodeUser(user.getUsername(), user.getPassword(), rols);
        principal.setFirstName(user.getFirstName());
        principal.setId(user.getId());
        principal.setLastName(user.getLastName());
        LOGGER.debug("Returning user details: {}", principal);
        return principal;
    }
}