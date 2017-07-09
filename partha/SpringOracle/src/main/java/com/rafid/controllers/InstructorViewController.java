package com.rafid.controllers;

import com.rafid.models.Course;
import com.rafid.models.Repositories;
import com.rafid.models.Users;
import com.rafid.repositories.*;
import com.rafid.util.Constants;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 * Created by ASUS on 21-May-17.
 */
@Controller
public class InstructorViewController {

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
    @RequestMapping("/traverseRepository")
    public String  traverseProject(HttpSession httpSession, Model model, @ModelAttribute("courseId") long courseId, RedirectAttributes redirectAttributes)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }
        Course course=courseRepository.findByCourseId(courseId).get(0);
        List<Repositories> repoList=repositoriesRepository.findByCourse(course);
        httpSession.setAttribute(Constants.User_List,repoList);
        redirectAttributes.addAttribute(Constants.currentCourseId,courseId);
        

        if(repoList.size()>0)
        {
            redirectAttributes.addAttribute(Constants.User_id,repoList.get(0).getUsers().getUserId());
        }
        else
        {
            redirectAttributes.addAttribute(Constants.User_id,null);
        }


        return "redirect:/enterEnrolleeRepository";
    }

    @RequestMapping("/enterEnrolleeRepository")
    public String  enrolleeRepository(@ModelAttribute("Change") String change, @ModelAttribute("currentCourseId") String courseId, @ModelAttribute("User_id") String userID, HttpSession httpSession, WebRequest webRequest)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        List<Repositories> userRepositoryList=null;
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }
       // userID= (String) redirectAttributes.getFlashAttributes().get(Constants.User_id);
        //Special case handle it

            if (userID == null || userID.length() == 0) {

            }
            /////////////////////////////////////
            // usersSet=(Set<Users>)redirectAttributes.getFlashAttributes().get(Constants.User_List);
            if (userRepositoryList == null) {
                userRepositoryList = (List<Repositories>) httpSession.getAttribute(Constants.User_List);
            }
            //courseId=(String)redirectAttributes.getFlashAttributes().get(Constants.currentCourseId);
            if (courseId == null || courseId.length() == 0) {
                courseId = (String) httpSession.getAttribute(Constants.currentCourseId);
            }

        httpSession.removeAttribute(Constants.holderStack);
        httpSession.removeAttribute(Constants.selectStack);
        httpSession.removeAttribute(Constants.userRepository);
        httpSession.removeAttribute(Constants.currentPath);


        httpSession.setAttribute(Constants.userName,name);
        httpSession.setAttribute(Constants.User_id,userID);
        httpSession.setAttribute(Constants.User_List,userRepositoryList);
        httpSession.setAttribute(Constants.currentCourseId,courseId);

        Course course=courseRepository.findByCourseId(Long.parseLong(courseId)).get(0);
        Users users=userRepository.findByUserId(Long.valueOf(userID));
        Repositories repositories=repositoriesRepository.findByCourseAndUsers(course,users).get(0);
        httpSession.setAttribute(Constants.userRepository,repositories.getRepositoryName());
        httpSession.setAttribute("CurrentEnrollee",users.getFirstName()+" "+users.getLastName());
        return "redirect:/traverseEnrolleeRepository";

        //path,select,stack,fit username,password,userid,repositoryName clear korte hobe
        /*     Attttttttttttttttention make a valid link*/



    }
    @RequestMapping("/traverseEnrolleeRepository")
    public String traverseEnrolleeRepository(@ModelAttribute("selects")String  selects, Model model, HttpSession httpSession, RedirectAttributes redirectAttributes)
    {
        long userid= Long.parseLong((String)httpSession.getAttribute(Constants.User_id));
        String gitUserName = (userRepository.findByUserId(userid).getGitUserId());
        String gitPassword = (userRepository.findByUserId(userid).getGitPassword());
        String path = (String) httpSession.getAttribute(Constants.currentPath);
        String repositoryName=(String)httpSession.getAttribute(Constants.userRepository);
        Stack<List<GHContent>> stateStack = (Stack) httpSession.getAttribute(Constants.holderStack);
        Stack<String> selectStack=(Stack)httpSession.getAttribute(Constants.selectStack);
        List<Repositories>userRepositoryList=(List<Repositories>)httpSession.getAttribute(Constants.User_List);
        String  enrolleeFullName=(String)httpSession.getAttribute("CurrentEnrollee");
        model.addAttribute("CurrentEnrollee",enrolleeFullName);
        model.addAttribute(Constants.User_List,userRepositoryList);
        if (stateStack == null) {
            stateStack = new Stack<>();
        }
        if (selectStack == null) {
            selectStack = new Stack<>();
        }
        selectStack.push(selects);
        httpSession.setAttribute(Constants.selectStack,selectStack);
//---------------------------------setting path and if first get repo list-----------------------------------------
        String newPath;
        if (path == null) {
            newPath = "/" + selects;
            httpSession.setAttribute(Constants.currentPath, newPath);


            GitHub gitHub = null;
            try {
                gitHub = GitHub.connectUsingPassword(gitUserName, gitPassword);
                Map<String, GHRepository> userRepositoryMap = null;
                for (; true; )//  retry
                {

                    try {
                        GHRepository ghRepository = gitHub.getMyself().getRepository(repositoryName);
                        stateStack.push(null);
                        List<GHContent> folderList =ghRepository.getDirectoryContent("/");
                        stateStack.push(folderList);
                        httpSession.setAttribute(Constants.holderStack,stateStack);
                        model.addAttribute(Constants.folderList,folderList);
                        return "/instructorProject";

                    } catch (SocketTimeoutException exception) {
                        System.out.println("Socket timed out retrying");
                        exception.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            newPath = path + "/" + selects;
            httpSession.removeAttribute(Constants.currentPath);
            httpSession.setAttribute(Constants.currentPath, newPath);
            List<GHContent> tempList=stateStack.lastElement();
            for(GHContent content:tempList)
            {
                if(content.getName().compareTo(selects)==0)
                {
                    if(content.isDirectory())
                    {
                        try {
                            List<GHContent> folderList =content.listDirectoryContent().asList();
                            stateStack.push(folderList);
                            httpSession.removeAttribute(Constants.holderStack);
                            httpSession.setAttribute(Constants.holderStack,stateStack);
                            model.addAttribute(Constants.folderList,folderList);
                            return  "/instructorProject";
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        try {
                            List<GHContent> temp=stateStack.lastElement();
                            stateStack.push(temp);
                            httpSession.removeAttribute(Constants.holderStack);
                            httpSession.setAttribute(Constants.holderStack,stateStack);
                            model.addAttribute(Constants.codeData, ProjectController.getStringFromInputStream(content.read()));
                            String as[] = content.getName().split("\\.");
                            model.addAttribute(Constants.lastCommit, content.getOwner().listCommits().asList().get(0).getCommitter().getName());
                            model.addAttribute(Constants.lang, as[as.length - 1]);
                            model.addAttribute(Constants.fileName, content.getName());
                            model.addAttribute(Constants.downloadFile, content.getDownloadUrl());
                            return "/instructorCodePage";
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        /* Attttttttttttttttttttttttention make a valid link */
        return "";
    }
    @PostMapping("/goBackInstructor")
    public  String onBack(HttpSession httpSession ,Model model,RedirectAttributes redirectAttributes)
    {
        System.out.println("received by go back");
        model.addAttribute(Constants.name_in_page, httpSession.getAttribute(Constants.user_name).toString());
        String userName = (String) httpSession.getAttribute(Constants.userName);
        if (userName == null || (userName.length() == 0)) {
            return "redirect:/login";
        }
        Stack<List<GHContent>> temp= (Stack<List<GHContent>>) httpSession.getAttribute(Constants.holderStack);
        Stack<String> select=(Stack)httpSession.getAttribute(Constants.selectStack);

        String name=select.lastElement();
        select.pop();
        if(select.size()>0) {

            redirectAttributes.addAttribute("selects", select.pop());

        }
        else
        {
            httpSession.removeAttribute(Constants.holderStack);
            httpSession.removeAttribute(Constants.currentPath);
            httpSession.removeAttribute(Constants.selectStack);
            httpSession.removeAttribute(Constants.userRepository);
;
            /* Attttttttttttttttttention clear all session variable */
            return "redirect:/course";
        }

        List<GHContent> tempObject=temp.pop();
        if(temp.lastElement()!=null)
        {
            temp.pop();
        }
        /*temp.pop();
        temp.pop();*/
        if((temp.size()<=0)||(temp.lastElement()==null))
        {
            httpSession.removeAttribute(Constants.holderStack);
            httpSession.removeAttribute(Constants.currentPath);
            return "redirect:/traverseEnrolleeRepository";
        }
        else
        {

            String path = (String) httpSession.getAttribute(Constants.currentPath);
            String  [] ar=path.split("/");
            String tempPath="";

            for(int i=1;i<ar.length-2;i++)
            {
                tempPath=tempPath+"/"+ar[i];
            }
            if(tempPath.length()==0)
            {
                tempPath=null;
            }
            httpSession.removeAttribute(Constants.currentPath);
            httpSession.setAttribute(Constants.currentPath,tempPath);
            httpSession.removeAttribute(Constants.holderStack);
            httpSession.setAttribute(Constants.holderStack,temp);


        }
        return "redirect:/traverseEnrolleeRepository";
    }

}
