package com.rafid.controllers;


import com.rafid.models.*;
import com.rafid.project.AddRepository;
import com.rafid.repositories.*;
import com.rafid.util.Constants;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * Created by ASUS on 4/29/2017.
 */
@Controller
public class CourseController {
    //@Autowired
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RepositoriesRepository repositoriesRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticesRepository noticesRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    @RequestMapping("/course")
    public String course(HttpSession session, Model model,@ModelAttribute("OptionalMessage")String  attribute) {

        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
            if((attribute==null)||(attribute.length()==0))
            {
                model.addAttribute("OptionalMessage","NotNecessary");
            }
            else
            {
                model.addAttribute("OptionalMessage",attribute);
            }
			List<Course> totalCourses = courseRepository.findAll();
			List<Course> enrolledCourses = new ArrayList<>();
			List<Course> instructCourses = new ArrayList<>();
            List<AddRepository>enrolledCoursesRepository=new ArrayList<>();
            List<Repositories> allRepositories;
            String userName = session.getAttribute(Constants.user_name).toString();
            Users users=userRepository.findByUserName(userName).get(0);
            int counter=0;
			if(!totalCourses.isEmpty()){
                for(Course c: totalCourses){
                    if(c.isUserInInstructors(userName)){
                        instructCourses.add(c);
                    }
                    else if(c.isUserInUsersSet(userName)){
                        enrolledCourses.add(c);
                        allRepositories=repositoriesRepository.findByCourseAndUsers(c,users);
                        counter=0;
                        for(Repositories temp:allRepositories)
                        {
                            if(temp.getCourse().equals(c))
                            {
                                enrolledCoursesRepository.add(new AddRepository(c.getCourseName(),temp.getRepositoryName(),true,c.getCourseId()));
                                counter++;
                                break;
                            }
                        }
                        if(counter==0)
                        {
                            enrolledCoursesRepository.add(new AddRepository(c.getCourseName(),null,false,c.getCourseId()));
                        }
                    }
                }
            }
            model.addAttribute(Constants.instructCourses, instructCourses);
            model.addAttribute(Constants.name_in_page, session.getAttribute(Constants.user_name).toString());
            model.addAttribute(Constants.courseContents, Constants.courseNormal);
            model.addAttribute(Constants.enrolledCourses, enrolledCourses);
            model.addAttribute(Constants.enrolledRepositories,enrolledCoursesRepository);

            return "course";
        }
        else {

            return "redirect:/login";
        }

    }

    //@Autowired
    @RequestMapping("/streamVideo")
    public String streamVideo() {
        return "streamVideo";

    }


    @PostMapping("/createNotice")
    public String createNotice(HttpSession session, Model model, @RequestParam("topic") String topic,
                               @RequestParam("noticeText") String noticeText,
                               @RequestParam("userName") String userName, @RequestParam("courseId") long courseId){
        Calendar cal = Calendar.getInstance();
        Date noticeDate = new Date(cal.getTimeInMillis());
        List<Users> result = userRepository.findByUserName(userName);
        List<Course> courseResult = courseRepository.findByCourseId(courseId);
        Users user = result.get(0);
        Course course = courseResult.get(0);
        Notices notice = new Notices(noticeText, topic, noticeDate, course, user);
        noticesRepository.save(notice);
        List<Tutorial> tutorialList = tutorialRepository.findByCourse(course);
        List<Notices> noticesList = noticesRepository.findByCourse(course);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseTutorials, tutorialList);
        }
        else model.addAttribute(Constants.courseTutorials, null);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseNotices, noticesList);
        }
        else model.addAttribute(Constants.courseNotices, null);
        model.addAttribute(Constants.name_in_page, userName);
        model.addAttribute(Constants.courseContents, Constants.courseIn);
        model.addAttribute(Constants.inCoursePrivilege, Constants.instructor);
        model.addAttribute(Constants.currentCourse, course);
        return "course";
    }

    @GetMapping("/enterCourse")
    public String enterCourse(HttpSession session, Model model, @RequestParam("courseId") long courseId,
                              @RequestParam("userType") String userType){
        //System.out.println("courseId = "+courseId);
        //System.out.println("user type = "+userType);
        List<Course> courseResult = courseRepository.findByCourseId(courseId);
        Course course = courseResult.get(0);
        String userName = session.getAttribute(Constants.user_name).toString();

        List<Tutorial> tutorialList = tutorialRepository.findByCourse(course);
        List<Notices> noticesList = noticesRepository.findByCourse(course);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseTutorials, tutorialList);
        }
        else model.addAttribute(Constants.courseTutorials, null);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseNotices, noticesList);
        }
        else model.addAttribute(Constants.courseNotices, null);
        model.addAttribute(Constants.name_in_page, userName);
        model.addAttribute(Constants.courseContents, Constants.courseIn);
        model.addAttribute(Constants.inCoursePrivilege, userType);
        //model.addAttribute(Constants.course_name, courseName);
        //long id = courses.get(0).getCourseId();

        model.addAttribute(Constants.currentCourse, course);
        return "course";
    }


    @PostMapping("/createCourse")
    public String createCourse(HttpSession session, Model model, @RequestParam("courseName") String courseName, @RequestParam("subject") String subject, @RequestParam("courseIntro") String courseIntro) {
        Course course = new Course(courseName, subject, courseIntro);
        String userName = session.getAttribute(Constants.user_name).toString();
        Users user;
        System.out.println("In create course "+userName);
        List<Users> result = userRepository.findByUserName(userName);
        if(!result.isEmpty()){
            user = result.get(0);
            System.out.println(user.getFirstName());
            course.getInstructors().add(user);
        }

        courseRepository.save(course);
        List<Tutorial> tutorialList = tutorialRepository.findByCourse(course);
        List<Notices> noticesList = noticesRepository.findByCourse(course);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseTutorials, tutorialList);
        }
        else model.addAttribute(Constants.courseTutorials, null);
        if(!tutorialList.isEmpty()){
            model.addAttribute(Constants.courseNotices, noticesList);
        }
        else model.addAttribute(Constants.courseNotices, null);
        model.addAttribute(Constants.name_in_page, userName);
        model.addAttribute(Constants.courseContents, Constants.courseIn);
        model.addAttribute(Constants.inCoursePrivilege, Constants.instructor);
        //model.addAttribute(Constants.course_name, courseName);
        //long id = courses.get(0).getCourseId();

        model.addAttribute(Constants.currentCourse, course);
        return "course";
    }

    @GetMapping("/preludeTocreateCourse")
    public String preludeToCourse(HttpSession session, Model model){
        model.addAttribute("name", session.getAttribute(Constants.user_name).toString());
        model.addAttribute("courseContents", Constants.courseCreate);
        return "course";
    }
    @RequestMapping("/addRepository")
    public String addRepository(@ModelAttribute("course_id") long courseId, @ModelAttribute("repository_name") String  repositoryName, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }
        Users user=userRepository.findByUserName(name).get(0);

        String password= user.getGitPassword();
        if(password==null||password.length()==0)
        {

            return "/gitLogin";
        }
        String userName=user.getGitUserId();
        GitHub gitHub=null;
        try {
            gitHub=GitHub.connectUsingPassword(userName,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            GHRepository ghRepository=gitHub.getMyself().getAllRepositories().get(repositoryName);
            if(ghRepository==null)
            {
                redirectAttributes.addAttribute("OptionalMessage","NotAvailable");
                return "redirect:/course";
            }
            redirectAttributes.addAttribute("OptionalMessage","Available");
            Course course=courseRepository.findByCourseId(courseId).get(0);
            Repositories repositories=new Repositories(ghRepository.getName(),ghRepository.gitHttpTransportUrl(),course,user);
            repositoriesRepository.save(repositories);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/course";

    }
    @RequestMapping("/changeRepository")
    public String changeRepository(@ModelAttribute("course_id") long courseId, @ModelAttribute("repository_name") String  repositoryName, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }

        Users user=userRepository.findByUserName(name).get(0);

        String password= user.getGitPassword();
        if(password==null||password.length()==0)
        {

            return "/gitLogin";
        }
        String userName=user.getGitUserId();
        GitHub gitHub=null;
        try {
            gitHub=GitHub.connectUsingPassword(userName,password);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            GHRepository ghRepository=gitHub.getMyself().getAllRepositories().get(repositoryName);
            if(ghRepository==null)
            {
                redirectAttributes.addAttribute("OptionalMessage","NotAvailable");
                return "redirect:/course";
            }
            redirectAttributes.addAttribute("OptionalMessage","Available");
            Course course=courseRepository.findByCourseId(courseId).get(0);
            Repositories oldRepository=repositoriesRepository.findByCourseAndUsers(course,user).get(0);
            oldRepository.setRepositoryName(ghRepository.getName());
            oldRepository.setRepositoryLink(ghRepository.gitHttpTransportUrl());
            repositoriesRepository.save(oldRepository);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/course";

    }


}

