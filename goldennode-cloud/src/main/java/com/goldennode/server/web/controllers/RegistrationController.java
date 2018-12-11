package com.goldennode.server.web.controllers;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.goldennode.server.entity.Authorities;
import com.goldennode.server.entity.Users;
import com.goldennode.server.repository.AuthorityRepository;
import com.goldennode.server.security.GoldenNodeUser;
import com.goldennode.server.security.services.UserService;
import com.goldennode.server.security.web.RegistrationForm;
import com.goldennode.server.security.web.validation.DuplicateEmailException;

@Controller
@RequestMapping(value = { "/register" })
@CrossOrigin(origins = "*")
public class RegistrationController {
    @RequestMapping(value = { "/temp" }, method = { RequestMethod.POST })
    @ResponseBody
    public ResponseEntity<Users> registerTemp(@RequestBody String data) throws IOException {
        Users registered = service.registerTempUser();
        return new ResponseEntity<Users>(registered, HttpStatus.OK);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String MODEL_NAME_REGISTRATION_DTO = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "registrationForm";
    protected static final String VIEW_NAME_LOGIN_PAGE = "login";
    protected static final String VIEW_NAME_HOME_PAGE = "/";
    @Autowired
    private UserService service;
    @Autowired
    private AuthorityRepository authorityRepository;

    /**
     * Renders the registration page.
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        LOGGER.debug("Rendering registration page.");
        model.addAttribute(MODEL_NAME_REGISTRATION_DTO, new RegistrationForm());
        return VIEW_NAME_REGISTRATION_PAGE;
    }

    /**
     * Processes the form submissions of the registration form.
     */
    @RequestMapping(method = RequestMethod.POST)
    public String registerPremium(@Valid @ModelAttribute("user") RegistrationForm userAccountData, BindingResult result, WebRequest request) throws DuplicateEmailException {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }
        LOGGER.debug("No validation errors found. Continuing registration process.");
        Users registered = createPremiumAccount(userAccountData, result);
        // If email address was already found from the database, render the form view.
        if (registered == null) {
            LOGGER.debug("An email address was found from the database. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
        }
        LOGGER.debug("Registered user account with information: {}", registered);
        // Logs the user in.
        logInUser(registered);
        LOGGER.debug("User {} has been signed in", registered);
        return "redirect:/";
    }

    /**
     * Creates a new user account by calling the service method. If the email address is found from the database, this method adds a field error to the email field of the form object.
     */
    private Users createPremiumAccount(RegistrationForm userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        Users registered = null;
        try {
            registered = service.registerPremiumUser(userAccountData);
        } catch (DuplicateEmailException ex) {
            LOGGER.debug("An email address: {} exists.", userAccountData.getEmail());
            addFieldError(MODEL_NAME_REGISTRATION_DTO, RegistrationForm.FIELD_NAME_EMAIL, userAccountData.getEmail(), ERROR_CODE_EMAIL_EXIST, result);
        }
        return registered;
    }

    private void logInUser(Users user) {
        LOGGER.info("Logging in user: {}", user);
        Set<Authorities> authorities = authorityRepository.findByUserId(user.getId());
        Set<GrantedAuthority> rols = new HashSet<GrantedAuthority>();
        for (Authorities authority : authorities) {
            rols.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        GoldenNodeUser goldenNodeUser = new GoldenNodeUser(user.getEmail(), user.getPassword(), rols);
        goldenNodeUser.setFirstName(user.getFirstName());
        goldenNodeUser.setId(user.getId());
        goldenNodeUser.setLastName(user.getLastName());
        LOGGER.debug("Logging in principal: {}", goldenNodeUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(goldenNodeUser, null, goldenNodeUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LOGGER.info("User: {} has been logged in.", goldenNodeUser);
    }

    private void addFieldError(String objectName, String fieldName, String fieldValue, String errorCode, BindingResult result) {
        LOGGER.debug("Adding field error object's: {} field: {} with error code: {}", objectName, fieldName, errorCode);
        FieldError error = new FieldError(objectName, fieldName, fieldValue, false, new String[] { errorCode }, new Object[] {}, errorCode);
        result.addError(error);
        LOGGER.debug("Added field error: {} to binding result: {}", error, result);
    }
}
