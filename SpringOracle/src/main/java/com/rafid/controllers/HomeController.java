package com.rafid.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String greeting(HttpSession session, Model model) {
        if ( session.getAttribute("username")!=null &&  !session.getAttribute("username").toString().isEmpty()) {
            model.addAttribute("name", session.getAttribute("username").toString());
            return "home";
        }
        else {
            return "redirect:/login";
        }
    }

}