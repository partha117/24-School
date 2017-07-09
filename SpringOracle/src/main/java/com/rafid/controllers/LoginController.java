package com.rafid.controllers;


import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/login")
    public String loginPage() {
        return "index";
    }   ///mamun changed this line <someone set it login, but there is no page named login.html

    @PostMapping("/login")
    public String loginCheck(HttpSession session, @RequestParam("userName") String userName, @RequestParam("password") String password) {
        List<Users> result = userRepository.findByUserNameAndPassword(userName,password);

        if (result.isEmpty()){
            return "redirect:/";
        }
        else {
            session.setAttribute(Constants.user_name, result.get(0).getUserName());
            System.out.println("User found with username: "+userName+" and password: "+password);
            return "redirect:/home";
        }
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(Constants.user_name);
        session.invalidate();
        return "redirect:/login";
    }

}
