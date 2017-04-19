package com.rafid.controllers;

import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ASUS on 4/18/2017.
 */
@Controller
public class RegistrationCotroller {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/registration")
    public String userRegistration(){
        return "registration";
    }

    @PostMapping("/")
    public String userSubmit(@ModelAttribute Users users, Model model){
        userRepository.save(users);
        model.addAttribute("name", users.getUserName());
        return "index";
    }

}
