package com.thingabled.server.security.social.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thingabled.commons.entity.Authorities;
import com.thingabled.commons.entity.Users;
import com.thingabled.commons.repository.AuthorityRepository;
import com.thingabled.commons.repository.UserRepository;
import com.thingabled.server.security.social.RegistrationForm;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Transactional
    public Users registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException {
        LOGGER.debug("Registering new user account with information: {}", userAccountData);

        if (emailExist(userAccountData.getEmail())) {
            LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
            throw new DuplicateEmailException("The email address: " + userAccountData.getEmail() + " is already in use.");
        }

        LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

        String encodedPassword = encodePassword(userAccountData);

        Users registered =Users.newEntity();
        registered.setEmail(userAccountData.getEmail());
        registered.setFirstName(userAccountData.getFirstName());
        registered.setLastName(userAccountData.getLastName());
        registered.setPassword_(encodedPassword);
        registered.setUsername(userAccountData.getEmail());
        registered.setEnabled(1);
         
        Authorities auth=Authorities.newEntity();
        auth.setUsername(userAccountData.getEmail());
        auth.setAuthority(Users.Role.ROLE_USER.toString());
        authorityRepository.save(auth);
        auth=Authorities.newEntity();
        auth.setUsername(userAccountData.getEmail());
        auth.setAuthority(Users.Role.ROLE_CLIENT.toString());
        authorityRepository.save(auth);

        if (userAccountData.isSocialSignIn()) {
        	registered.setSignInProvider(userAccountData.getSignInProvider());
        }

        LOGGER.debug("Persisting new user with information: {}", registered);

        return repository.save(registered);
    }

    private boolean emailExist(String email) {
        LOGGER.debug("Checking if email {} is already found from the database.", email);

        Users user = repository.findByEmail(email);

        if (user != null) {
            LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
            return true;
        }

        LOGGER.debug("No user account found with email: {}. Returning false.", email);

        return false;
    }

    private String encodePassword(RegistrationForm dto) {
        String encodedPassword = null;

        if (dto.isNormalRegistration()) {
            LOGGER.debug("Registration is normal registration. Encoding password.");
            encodedPassword = passwordEncoder.encode(dto.getPassword());
        }
        
        return encodedPassword;
    }
}
