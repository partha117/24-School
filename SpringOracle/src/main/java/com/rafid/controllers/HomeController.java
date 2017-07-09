package com.rafid.controllers;


import com.rafid.models.Course;
import com.rafid.models.Users;
import com.rafid.repositories.CourseRepository;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String greeting(HttpSession session, Model model) {
        System.out.println("In the homepage");

        if ( session.getAttribute("username")!=null &&  !session.getAttribute("username").toString().isEmpty()) {
            model.addAttribute("name", session.getAttribute("username").toString());
            System.out.println("User's home page. Username: "+session.getAttribute("username").toString());
            return "home";
        }
        else {
           // System.out.println("Session empty. Username: "+session.getAttribute("username").toString());
            return "redirect:/login";
        }
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @RequestMapping("/search")
    public String search(HttpSession session, Model model, @RequestParam("search") String searchItem){
        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()){
            List<Users> userResults = userRepository.findByUserNameContainsOrFirstNameContainsOrLastNameContainsAllIgnoreCase(searchItem, searchItem, searchItem);
            List<Course>courseResults = courseRepository.findByCourseNameIgnoreCaseContaining(searchItem);
            model.addAttribute(Constants.userSearchList, userResults);
            model.addAttribute(Constants.courseSearchList, courseResults);
            model.addAttribute(Constants.name_in_page, session.getAttribute(Constants.user_name).toString());
            return "searchResults";
        }
        else{
            return "redirect:/login";
        }

    }



}