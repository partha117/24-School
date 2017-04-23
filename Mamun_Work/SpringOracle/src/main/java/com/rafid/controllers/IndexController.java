package com.rafid.controllers;

/**
 * Created by ASUS on 4/17/2017.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @Autowired
    @RequestMapping("/")
    String index() {
        return "redirect:/home";  ///changed it- mamun, if session not empty, index redirects to the home page

    }
}