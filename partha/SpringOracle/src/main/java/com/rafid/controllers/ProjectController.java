package com.rafid.controllers;

import com.rafid.models.Users;
import com.rafid.project.repositoryDetails;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constant;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by ASUS on 29-Apr-17.
 */
@Controller
public class ProjectController {
    @Autowired
    private UserRepository userRepository;
    @RequestMapping("/project")
    public String projectTab(HttpSession httpSession,Model model)
    {
        String name=(String)httpSession.getAttribute(Constant.userName);
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }
        else
        {
            List<Users>user=userRepository.findByUserName(name);
            if(user.size()!=0)
            {
                if((user.get(0).getGitUserId()==null)||user.get(0).getGitUserId().length()==0)
                {
                    return "gitLogin";
                }
                else
                {
                    String gitUserName=(userRepository.findByUserName(name).get(0).getGitUserId());
                    String gitPassword=(userRepository.findByUserName(name).get(0).getGitPassword());
                    GitHub gitHub = null;
                    try {
                        gitHub = GitHub.connectUsingPassword(gitUserName,gitPassword);
                        Map<String, GHRepository> userRepositoryMap= gitHub.getMyself().getAllRepositories();
                        //userRepositoryMap.get(0).queryCommits().list().asList().get(0).getCommitShortInfo().getMessage();
                        model.addAttribute(Constant.gitUserRepositoryDetails, repositoryDetails.getAsList(userRepositoryMap));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return "/projectHome";
                }
            }
            else
            {
                return "redirect:/login";
            }
        }

    }
    @RequestMapping("/projectLogIn")
    public String projectLogIn(@RequestParam("Username") String gitUserName, @RequestParam("Password") String gitPassword, HttpSession httpSession, Model model)
    {
        String username=(String)httpSession.getAttribute(Constant.userName);
        if(username==null ||(username.length()==0))
        {
            return "redirect:/login";
        }
        else
        {
            List<Users> users = userRepository.findByUserName(username);
            if(!users.isEmpty()){
                users.get(0).setGitUserId(gitUserName);
                users.get(0).setGitPassword(gitPassword);
                userRepository.save(users.get(0));
                GitHub gitHub= null;
                try {
                    gitHub = GitHub.connectUsingPassword(gitUserName,gitPassword);
                    Map<String, GHRepository> userRepositoryMap=null;
                    for(;true;)
                    {

                        try
                        {
                            userRepositoryMap = gitHub.getMyself().getAllRepositories();
                            break;
                        }
                        catch (SocketTimeoutException exception)
                        {
                            System.out.println("Socket timed out retrying");
                            exception.printStackTrace();
                        }
                    }
                    model.addAttribute(Constant.gitUserRepositoryDetails, repositoryDetails.getAsList(userRepositoryMap));


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return "redirect:/project";
            }
            else
            {
                return "redirect:/login";
            }
        }
    }
    @RequestMapping("/repoTraverse")
    public String repositoryTraverse(@RequestParam("selects")String  selects,Model model,HttpSession httpSession) {
        String name = (String) httpSession.getAttribute(Constant.userName);
        if (name == null || (name.length() == 0)) {
            return "redirect:/login";
        }
        else
        {
            List<Users> user = userRepository.findByUserName(name);
            if (user.size() != 0)
            {
                if ((user.get(0).getGitUserId() == null) || user.get(0).getGitUserId().length() == 0) {
                    return "/gitLogin";
                }
                else {
                    String gitUserName = (userRepository.findByUserName(name).get(0).getGitUserId());
                    String gitPassword = (userRepository.findByUserName(name).get(0).getGitPassword());
                    String path = (String) httpSession.getAttribute(Constant.currentPath);
                    String newPath;
                    if (path == null) {
                        newPath = "/"+selects;
                        httpSession.setAttribute(Constant.currentPath, newPath);
                    } else {
                        newPath = path + "/" + selects;
                        httpSession.removeAttribute(Constant.currentPath);
                        httpSession.setAttribute(Constant.currentPath, newPath);
                    }
                    GitHub gitHub = null;
                    try {
                        gitHub = GitHub.connectUsingPassword(gitUserName, gitPassword);
                        Map<String, GHRepository> userRepositoryMap=null;
                        for(;true;)
                        {

                           try
                           {
                               userRepositoryMap = gitHub.getMyself().getAllRepositories();
                               break;
                           }
                           catch (SocketTimeoutException exception)
                           {
                               System.out.println("Socket timed out retrying");
                               exception.printStackTrace();
                           }
                        }

                        System.out.println("Now path "+path);
                        for (String key : userRepositoryMap.keySet()) {

                                if(path==null)
                                {
                                    if(key.compareTo(selects)==0)
                                    {
                                        List<GHContent> folderList = userRepositoryMap.get(key).getDirectoryContent("/");
                                        // folderList.get(0).getName();
                                        model.addAttribute(Constant.folderList, folderList);
                                        return "/repository";
                                    }
                                }
                                else
                                {
                                    String[] temp = newPath.split("/");
                                    if (key.compareTo(temp[1]) == 0) {
                                        List<GHContent> folderList = userRepositoryMap.get(key).getDirectoryContent("/");
                                        System.out.println("Now temp length" + temp.length);
                                        for (int i = 1; i < temp.length; i++) {
                                            System.out.println("Now in level " + temp[i]);
                                            for (GHContent content : folderList) {
                                                if (content.getName().compareTo(temp[i]) == 0) {
                                                    if(content.isDirectory()) {
                                                        folderList = content.listDirectoryContent().asList();
                                                    }
                                                    else
                                                    {
                                                        model.addAttribute(Constant.codeData,getStringFromInputStream(content.read()));
                                                        String as[]=content.getName().split("\\.");
                                                        model.addAttribute("lang",as[as.length-1]);
                                                        return "/codePage";
                                                    }

                                                }
                                            }
                                        }
                                        model.addAttribute(Constant.folderList, folderList);
                                        return "/repository";
                                    }
                                }


                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                return "/repository";
            }
            return "redirect:/login";
        }
    }
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {

                sb.append("\n"+line);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }


}
