package com.rafid.controllers;


import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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



}