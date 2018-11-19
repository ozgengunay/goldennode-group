package com.goldennode.server.security.hmac;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.repository.UserRepository;
import com.goldennode.server.security.GoldenNodeUser;

@Service
public class HmacUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String publicKey) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(publicKey);
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(Users.Role.ROLE_CLIENT.toString()));
        GoldenNodeUser principal = new GoldenNodeUser(user.getUsername(), user.getPassword(), authorities);
        principal.setFirstName(user.getFirstName());
        principal.setId(user.getId());
        principal.setLastName(user.getLastName());
        return principal;
    }
}
