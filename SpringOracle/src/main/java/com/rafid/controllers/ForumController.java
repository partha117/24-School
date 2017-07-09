package com.rafid.controllers;

import com.rafid.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by ab9ma on 4/29/2017.
 */
@Controller
public class ForumController {

    @RequestMapping("/forum")
    public String getForum(HttpSession session, Model model) {
        System.out.println("In the homepage");

        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            model.addAttribute("name", session.getAttribute(Constants.user_name).toString());
            return "forum";
        }
        else {
            return "redirect:/login";
        }
    }
}
