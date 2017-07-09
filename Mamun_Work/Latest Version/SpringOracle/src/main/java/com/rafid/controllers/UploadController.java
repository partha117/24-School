package com.rafid.controllers;

/**
 * Created by ab9ma on 4/24/2017.
 */
import com.rafid.models.Users;
import com.rafid.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Controller
public class UploadController {
    @Autowired
    UserRepository userRepository;

    //Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "F://temp//";

  /*  @GetMapping("/")
    public String index() {
        return "upload";
    }
*/
    //@RequestMapping(value = "/upload", method = RequestMethod.POST)
    @PostMapping("/uploadPropic") // //new annotation since 4.3
    public @ResponseBody String singleFileUpload(HttpSession session, @RequestParam("file") MultipartFile file, @RequestParam("dummy") String dummy,
                            RedirectAttributes redirectAttributes) {

        System.out.println("Got propic upload request");
        System.out.println(dummy);
        File tempFile = new File(UPLOADED_FOLDER);
        if(!tempFile.exists()){
            tempFile.mkdirs();
        }

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            System.out.println("propic uplaod failed");
            return "failure";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

     ///the following lines of codes are added for saving the file into the database-------------

            List<Users> users = userRepository.findByUserName(session.getAttribute("username").toString());
            if(!users.isEmpty()){
                users.get(0).setProfilePic(bytes);
                userRepository.save(users.get(0));
                System.out.println("propic upload successful");
                return "success";
            }

            ///database saving end

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("propic upload failed");
        return "failure";
    }

   /* @GetMapping("/")
    public String uploadStatus() {
        return "";
    }
*/
}