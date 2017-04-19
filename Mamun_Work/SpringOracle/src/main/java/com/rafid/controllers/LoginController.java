package com.rafid.controllers;

import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;

/**
 * Created by ab9ma on 4/19/2017.
 */
@Controller
public class LoginController {
    @Autowired
    UserRepository userRepository;
    @RequestMapping("/login")
    public String userLogin() {
        return "login";
    }
/*
    @PostMapping("/login")
    public @ResponseBody Iterable<Users> loginCheck(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        return userRepository.findByUserNameAndPassword(userName,password);
    }
*/
    @PostMapping("/login")
    public String checkLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, Model model){
        Iterable<Users> usersIterable = userRepository.findByUserNameAndPassword(userName, password);
        Iterator<Users> users = usersIterable.iterator();

       System.out.println(users.hasNext());

       if(users.hasNext()){
           model.addAttribute("name", userName);
           return "index";
       }
        return "login";
    }


}
