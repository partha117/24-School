package com.rafid.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ab9ma on 4/20/2017.
 */

@Controller
public class HomeController {
    @RequestMapping(path="/home")
    public String homePage(){
        return "home";
    }
}
