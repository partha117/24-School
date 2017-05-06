package com.rafid.controllers;


import com.rafid.models.Course;
import com.rafid.models.Notices;
import com.rafid.models.Tutorial;
import com.rafid.models.Users;
import com.rafid.repositories.CourseRepository;
import com.rafid.repositories.NoticesRepository;
import com.rafid.repositories.TutorialRepository;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 4/29/2017.
 */
@Controller
public class CourseController {
    //@Autowired
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NoticesRepository noticesRepository;

    @Autowired
    private TutorialRepository tutorialRepository;

    @RequestMapping("/course")
    public String course(HttpSession session, Model model) {
        if ( session.getAttribute(Constants.user_name)!=null &&  !session.getAttribute(Constants.user_name).toString().isEmpty()) {
			List<Course> totalCourses = courseRepository.findAll();
			List<Course> enrolledCourses = new ArrayList<>();
			List<Course> instructCourses = new ArrayList<>();
            String userName = session.getAttribute(Constants.user_name).toString();
			if(!totalCourses.isEmpty()){
                for(Course c: totalCourses){
                    if(c.isUserInInstructors(userName)){
                        instructCourses.add(c);
                    }
                    else if(c.isUserInUsersSet(userName)){
                        enrolledCourses.add(c);
                    }
                }
            }
            model.addAttribute(Constants.instructCourses, instructCourses);
            model.addAttribute(Constants.name_in_page, session.getAttribute(Constants.user_name).toString());
            model.addAttribute(Constants.courseContents, Constants.courseNormal);
            model.addAttribute(Constants.enrolledCourses, enrolledCourses);
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


}

