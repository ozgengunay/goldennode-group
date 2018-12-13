package com.goldennode.server.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.goldennode.server.entity.Users;
import com.goldennode.server.repository.UserRepository;
import com.goldennode.server.security.GoldenNodeUser;

@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    protected static final String VIEW_NAME_HOMEPAGE = "home";
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        LOGGER.debug("Rendering homepage.");
        GoldenNodeUser userDetails = (GoldenNodeUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Users user = userRepository.findByUsername(userDetails.getUsername());
        model.addAttribute("apiKey", user.getApiKey());
        model.addAttribute("secretKey", user.getSecretKey());
        return VIEW_NAME_HOMEPAGE;
    }

    @RequestMapping("favicon.ico")
    public String favicon() {
        return "forward:/static/img/favicon.ico";
    }
}
