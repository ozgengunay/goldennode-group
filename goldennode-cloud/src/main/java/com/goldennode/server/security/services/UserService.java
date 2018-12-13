package com.goldennode.server.security.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.goldennode.server.entity.Authorities;
import com.goldennode.server.entity.Users;
import com.goldennode.server.repository.AuthorityRepository;
import com.goldennode.server.repository.UserRepository;
import com.goldennode.server.security.KeyGenerator;
import com.goldennode.server.security.web.RegistrationForm;
import com.goldennode.server.security.web.validation.DuplicateEmailException;

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
    public Users registerPremiumUser(RegistrationForm userAccountData) throws DuplicateEmailException {
        LOGGER.debug("Registering premium user account with information: {}", userAccountData);
        if (emailExist(userAccountData.getEmail())) {
            LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());
            throw new DuplicateEmailException("The email address: " + userAccountData.getEmail() + " is already in use.");
        }
        LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());
        String encodedPassword = encodePassword(userAccountData);
        Users registered = Users.newEntity();
        registered.setEmail(userAccountData.getEmail());
        registered.setFirstName(userAccountData.getFirstName());
        registered.setLastName(userAccountData.getLastName());
        registered.setPassword(encodedPassword);
        registered.setUsername(userAccountData.getEmail());
        registered.setApiKey(KeyGenerator.generateApiKey());
        registered.setSecretKey(KeyGenerator.generateSecretKey());
        registered.setEnabled(1);
        Authorities auth = Authorities.newEntity();
        auth.setUserId(registered.getId());
        auth.setAuthority(Users.Role.ROLE_PREMIUM_USER.toString());
        authorityRepository.save(auth);
        LOGGER.debug("Persisting new user with information: {}", registered);
        return repository.save(registered);
    }

    @Transactional
    public Users registerFreemiumUser() {
        LOGGER.debug("Registering freemium user");
        Users registered = Users.newEntity();
        registered.setFirstName("freemium");
        registered.setLastName("freemium");
        registered.setApiKey(KeyGenerator.generateApiKey());
        registered.setSecretKey(KeyGenerator.generateSecretKey());
        registered.setEnabled(1);
        Authorities auth = Authorities.newEntity();
        auth.setUserId(registered.getId());
        auth.setAuthority(Users.Role.ROLE_FREEMIUM_USER.toString());
        authorityRepository.save(auth);
        LOGGER.debug("Persisting freemium user with information: {}", registered);
        return repository.save(registered);
    }
    
    @Transactional
    public Users registerTempUser() {
        LOGGER.debug("Registering temp user");
        Users registered = Users.newEntity();
        registered.setFirstName("temp");
        registered.setLastName("temp");
        registered.setApiKey(KeyGenerator.generateApiKey());
        registered.setSecretKey(KeyGenerator.generateSecretKey());
        registered.setEnabled(1);
        Authorities auth = Authorities.newEntity();
        auth.setUserId(registered.getId());
        auth.setAuthority(Users.Role.ROLE_TEMP_USER.toString());
        authorityRepository.save(auth);
        LOGGER.debug("Persisting temp user with information: {}", registered);
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
        encodedPassword = passwordEncoder.encode(dto.getPassword());
        return encodedPassword;
    }
}