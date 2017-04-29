package com.rafid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by ab9ma on 4/29/2017.
 */

@Controller
public class ProfileController {


        @RequestMapping("/profile")
        public String getProfile(HttpSession session, Model model) {
            System.out.println("In the homepage");

            if ( session.getAttribute("username")!=null &&  !session.getAttribute("username").toString().isEmpty()) {
                model.addAttribute("name", session.getAttribute("username").toString());
                return "profile";
            }
            else {
                return "redirect:/login";
            }
        }




}
