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
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ASUS on 4/29/2017.
 */
@Controller

public class VideoUploadController {
    @Autowired
    private TutorialRepository tutorialRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private NoticesRepository noticesRepository;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "./src/main/resources/static/media/";

    /*  @GetMapping("/")
      public String index() {
          return "upload";
      }
  */
    //@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PostMapping("/uploadVideo") // //new annotation since 4.3
    public String singleFileUpload(HttpSession session, Model model, @RequestParam("file") MultipartFile file,
                                   @RequestParam("tutorialsName") String tutorialsName,
                                   @RequestParam("tutorialsSubject") String tutorialsSubject,
                                   @RequestParam("userName") String userName, @RequestParam("courseId") long courseId
                                   ){


        String currentDateTime = new SimpleDateFormat("yyMMddHHmmss").format(new Date());
        String fileName = currentDateTime+file.getOriginalFilename();
        File tempFile = new File(UPLOADED_FOLDER + fileName);
        String videoUrl = fileName;
        if (file.isEmpty()) {
            List<Course> courseResult = courseRepository.findByCourseId(courseId);
            Course course = courseResult.get(0);
            model.addAttribute(Constants.name_in_page, userName);
            model.addAttribute(Constants.courseContents, Constants.courseIn);
            model.addAttribute(Constants.inCoursePrivilege, Constants.instructor);
            model.addAttribute(Constants.currentCourse, course);
            return "redirect:/course";
        }

        try {
           /* if(!tempFile.exists()){
                tempFile.createNewFile();
                System.out.println(tempFile.getAbsolutePath());
            }*/


            // Get the file and save it somewhere
            //
            byte[] bytes = file.getBytes();
            FileOutputStream fos = new FileOutputStream(tempFile);
            System.out.println(bytes);
            fos.write(bytes);
            fos.flush();
            fos.close();
            ///the following lines of codes are added for saving the file into the database-------------
//
//            //List<Users> users = userRepository.findByUserName(session.getAttribute("username").toString());
//            if(!users.isEmpty()){
//                users.get(0).setProfilePic(bytes);
//                userRepository.save(users.get(0));
//            }

            ///database saving end

//            redirectAttributes.addFlashAttribute("message",
//                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Users> result = userRepository.findByUserName(userName);
        List<Course> courseResult = courseRepository.findByCourseId(courseId);
        Users user = result.get(0);
        Course course = courseResult.get(0);
        Tutorial tutorial = new Tutorial(tutorialsName, tutorialsSubject, videoUrl, course, user);
        tutorialRepository.save(tutorial);
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

   /* @GetMapping("/")
    public String uploadStatus() {
        return "";
    }
*/
}
