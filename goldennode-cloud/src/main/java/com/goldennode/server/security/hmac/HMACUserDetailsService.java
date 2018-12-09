package com.goldennode.server.security.hmac;

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
public class HMACUserDetailsService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(HMACUserDetailsService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String publicKey) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by publicKey: {}", publicKey);
        Users user = userRepository.findByPublicKey(publicKey);
        LOGGER.debug("Found user: {}", user);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with publicKey: " + publicKey);
        }
        Set<Authorities> authorities = authorityRepository.findByUserId(user.getId());
        Set<GrantedAuthority> rols = new HashSet<GrantedAuthority>();
        for (Authorities authority : authorities) {
            rols.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        GoldenNodeUser principal = new GoldenNodeUser(user.getPublicKey(), user.getPrivateKey(), rols);
        principal.setFirstName(user.getFirstName());
        principal.setId(user.getId());
        principal.setLastName(user.getLastName());
        LOGGER.debug("Returning user details: {}", principal);
        return principal;
    }
}
