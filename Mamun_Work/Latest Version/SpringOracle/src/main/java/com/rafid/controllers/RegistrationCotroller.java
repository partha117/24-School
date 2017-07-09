package com.rafid.controllers;

import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ASUS on 4/18/2017.
 */
@Controller
public class RegistrationCotroller {
    @Autowired
    UserRepository userRepository;

    @RequestMapping("/registration")
    public String userRegistration(){
        return "redirect:/";
    }

    @RequestMapping("/registration2")
    public String userRegistration2(HttpSession session, Model model){
        String userName = (String) session.getAttribute(Constants.user_name);
        if(userName!=null && !userName.isEmpty()){
            model.addAttribute("name", userName);
            return "registration2";
        }
        else
            return "redirect:/";
    }

    @PostMapping("/registration2")
    public String registrationHalfDone(HttpSession session, @ModelAttribute Users users, Model model){
        userRepository.save(users);
        model.addAttribute("name", users.getUserName());
        session.setAttribute(Constants.user_name, users.getUserName());

        return "registration2";
    }

    @PostMapping("/completeProfile")
    public String completeProfile(@RequestParam("profession") String profession, @RequestParam("gender") String gender,
                                  @RequestParam("country") String country, @RequestParam("state") String state,
                                  @RequestParam("city") String city, @RequestParam("zipCode") String zipCode,
                                  @RequestParam("userName") String userName) { ///hidden input username------------


        System.out.println("Completing profile of "+userName);
        List<Users> users = userRepository.findByUserName(userName);
        System.out.println(profession+" "+gender+" "+country+" "+state+" "+city+" "+zipCode);

        if(!users.isEmpty()){
            System.out.println("got you "+userName);
           Users user = users.get(0);

            user.setProfession(profession);
           user.setGender(gender);
           user.setCountry(country);
            user.setState(state);
          user.setCity(city);
          user.setZipCode(zipCode);

          userRepository.save(user);
        }
        return "redirect:/";

    }

}
