package com.goldennode.server.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import com.goldennode.server.common.ResponseEntity;
import com.goldennode.server.common.StatusCode;
import com.goldennode.server.entity.Authorities;
import com.goldennode.server.entity.Users;

@RestController
@RequestMapping(value = { "/goldennode/register" })
@CrossOrigin(origins = "*")
public class RegistrationController {
    @RequestMapping(value = { "/anonymous" }, method = { RequestMethod.POST })
    public ResponseEntity regis(@PathVariable("mapId") String mapId, @RequestBody String data) throws IOException {
        
        return new ResponseEntity(null, StatusCode.SUCCESS);
    }
    
    

    /**
     * Processes the form submissions of the registration form.
     */
    @RequestMapping(value ="/register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute("user") RegistrationForm userAccountData,
                                      BindingResult result,
                                      WebRequest request) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("No validation errors found. Continuing registration process.");

        Users registered = createUserAccount(userAccountData, result);

        //If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }

        LOGGER.debug("Registered user account with information: {}", registered);

        //Logs the user in.
        logInUser(registered);
        LOGGER.debug("User {} has been signed in", registered);
        //If the user is signing in by using a social provider, this method call stores
        //the connection to the UserConnection table. Otherwise, this method does not
        //do anything.
        
        ProviderSignInUtils.handlePostSignUp(registered.getEmail(), request);

        return "redirect:/";
    }

    /**
     * Creates a new user account by calling the service method. If the email address is found
     * from the database, this method adds a field error to the email field of the form object.
     */
    private Users createUserAccount(RegistrationForm userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        Users registered = null;

        try {
            registered = service.registerNewUserAccount(userAccountData);
        }
        catch (DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(
                    MODEL_NAME_REGISTRATION_DTO,
                    RegistrationForm.FIELD_NAME_EMAIL,
                    userAccountData.getEmail(),
                    ERROR_CODE_EMAIL_EXIST,
                    result);
        }

        return registered;
    }
    
    private void logInUser(Users user) {
        LOGGER.info("Logging in user: {}", user);
        
        Set<Authorities> authorities= authorityRepository.findByUsername(user.getEmail());
        Set<GrantedAuthority> rols = new HashSet<GrantedAuthority>();
        for (Authorities authority:authorities) {
            rols.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
 
        ThingabledUserDetails userDetails = new ThingabledUserDetails(user.getEmail(),user.getPassword_(),rols);
        userDetails.setFirstName(user.getFirstName());
        userDetails.setId(user.getId());
        userDetails.setLastName(user.getLastName());
        userDetails.setSocialSignInProvider(user.getSignInProvider());
        
        LOGGER.debug("Logging in principal: {}", userDetails);

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        LOGGER.info("User: {} has been logged in.", userDetails);
    }
    
}
