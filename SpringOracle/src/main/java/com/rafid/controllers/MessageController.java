package com.rafid.controllers;


import com.rafid.models.Message;
import com.rafid.models.Users;
import com.rafid.repositories.MessageRepository;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private UserRepository userRepo;

    @RequestMapping("/messages")
    public String greeting(HttpSession session, Model model) {
        System.out.println("In the message page");

        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            model.addAttribute("name", session.getAttribute(Constants.user_name).toString());

            return "messages";
        }
        else {
            // System.out.println("Session empty. Username: "+session.getAttribute("username").toString());
            return "redirect:/login";
        }
    }
    @PostMapping("/sendMessage")
    public String sendMessage(HttpSession session, @ModelAttribute Message message, @RequestParam String receiverName){

        String sender = (String) session.getAttribute(Constants.user_name);
        message.setSender(userRepo.findByUserName(sender).get(0));
        message.setReceiver(userRepo.findByUserName(receiverName).get(0));

        System.out.println("Got it: "+sender+" says \""+message.getReceiver()+"\" to "+message.getContent());

        messageRepo.save(message);


        return "redirect:/messages";

    }

    @GetMapping("/getMessages")
    public @ResponseBody String getMessages(HttpSession session) throws JSONException {

        String myName = (String) session.getAttribute(Constants.user_name);
        if(myName==null || myName.isEmpty()) return "error";
        Users me = userRepo.findByUserName(myName).get(0);

        String text = "";
        List<Message> messages = messageRepo.findBySender(me);
        List<Message> received = messageRepo.findByReceiver(me);

        messages.addAll(received);
        Collections.sort(messages);


        JSONObject allMessages = new JSONObject();
        allMessages.put("total", messages.size());
        JSONArray array = new JSONArray();
        for(int i=0; i<messages.size(); i++){
            Message message = messages.get(i);

            JSONObject json = new JSONObject();
            json.put("content", message.getContent());
            json.put("date", message.getSentDate());
            if(message.getSender().getUserName().equals(myName)){
                json.put("type", "sent");
                json.put("with", message.getReceiver().getUserName());
            }
            else {
                json.put("type", "received");
                json.put("with", message.getSender().getUserName());
            }

            array.put(json);
        }
        allMessages.put("messages", array);
        text= allMessages.toString();


        System.out.println(text);

        return text;
    }

}