package com.rafid.controllers;

import com.rafid.models.Tutorial;
import com.rafid.repositories.TutorialRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ASUS on 4/30/2017.
 */
@Controller
public class TutorialController {
    @Autowired
    private TutorialRepository tutorialRepository;
    @GetMapping("/showTutorial")
    String tutorial(HttpSession session, Model model,
                    @RequestParam("tutorialId") long tutorialsId) {
        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            List<Tutorial> tutorialResult = tutorialRepository.findByTutorialsId(tutorialsId);
            Tutorial tutorial = tutorialResult.get(0);
            model.addAttribute(Constants.name_in_page, session.getAttribute(Constants.user_name).toString());
            model.addAttribute(Constants.videoUrl, tutorial.getVideoUrl());
            System.out.println("url = "+tutorial.getVideoUrl());
            model.addAttribute(Constants.currTutorial, tutorial);
            return "tutorial";
        }
        else {

            return "redirect:/login";
        }


    }
    @RequestMapping("/tutorial")
    String tutorial(HttpSession session, Model model
                    ) {


            return "redirect:/login";


    }
}
