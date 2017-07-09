package com.rafid.controllers;

import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;

/**
 * Created by ab9ma on 4/29/2017.
 */

@Controller
public class ProfileController {

    @Autowired
    private UserRepository userRepository;

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


    @RequestMapping("/getPropic")
    public @ResponseBody
    String getPropic(HttpSession session){

        System.out.println("got propic download request");
        if(session.getAttribute("username")!=null){
            List<Users> users = userRepository.findByUserName(session.getAttribute("username").toString());
            if(!users.isEmpty()){
                System.out.println("downloading propic");
                byte[] propic = users.get(0).getProfilePic();
                if(propic!=null)
                    return  Base64.getEncoder().encodeToString(propic);
            }
        }

        return "";
    }

    @RequestMapping("/getProfileInfo")
    public @ResponseBody
    String getProfileInfo(HttpSession session){

        System.out.println("got profile info download request");
        if(session.getAttribute(Constants.user_name)!=null){
            List<Users> users = userRepository.findByUserName(session.getAttribute(Constants.user_name).toString());
            if(!users.isEmpty()){
                Users user = users.get(0);
                String values = user.getFirstName()+" "+user.getLastName()+":"+
                        user.getGender()+":"+
                        user.getBirthDate()+":"+
                        user.getProfession()+":"+
                        user.getCountry()+":"+
                        user.getState()+":"+
                        user.getCity()+":"+
                        user.getZipCode()+":"+
                        user.getUserName()+":"+
                        user.getEmail()+":"+
                        user.getGitUserId();
                System.out.println("Returning "+values);
                return values;
            }
        }

        return "";
    }




}
