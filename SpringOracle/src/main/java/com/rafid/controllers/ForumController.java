package com.rafid.controllers;

import com.rafid.models.Answer;
import com.rafid.models.Question;
import com.rafid.models.Users;
import com.rafid.repositories.AnswerRepository;
import com.rafid.repositories.QuestionRepository;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ab9ma on 4/29/2017.
 */
@Controller
public class ForumController {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnswerRepository answerRepository;

    @RequestMapping("/forum")
    public String getForum(HttpSession session, Model model) {
        //System.out.println("In the homepage");

        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            model.addAttribute("name", session.getAttribute(Constants.user_name).toString());
            List<Question> questions = questionRepository.findAll();
            model.addAttribute(Constants.questionPosts, questions);
            return "forum";
        }
        else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/postQuestion")
    public String postQuestion(HttpSession session, Model model, @RequestParam("post") String post){
        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            String userName = session.getAttribute(Constants.user_name).toString();
            List<Users> users = userRepository.findByUserName(userName);
            Users user = users.get(0);
            //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Question question = new Question(post, user, date);
            questionRepository.save(question);
            return "forum";
        }
        else {
            return "redirect:/login";
        }
    }

    @RequestMapping("/writeComment")
    public String writeComment(HttpSession session, Model model, @RequestParam("comment") String comment, @RequestParam("postId") long postId){
        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            String userName = session.getAttribute(Constants.user_name).toString();
            List<Users> users = userRepository.findByUserName(userName);
            Users user = users.get(0);
            //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Date date = new Date();
            Question question = questionRepository.findById(postId);
            Answer answer = new Answer(comment, user, date);
            answerRepository.save(answer);
            question.getAnswers().add(answer);
            questionRepository.save(question);
            return "forum";
        }
        else {
            return "redirect:/login";
        }
    }

}
