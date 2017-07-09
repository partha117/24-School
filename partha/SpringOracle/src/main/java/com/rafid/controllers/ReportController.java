package com.rafid.controllers;

import com.rafid.Report.LineData;
import com.rafid.Report.PieData;
import com.rafid.models.Course;
import com.rafid.models.Repositories;
import com.rafid.models.Users;
import com.rafid.repositories.CourseRepository;
import com.rafid.repositories.RepositoriesRepository;
import com.rafid.repositories.UserRepository;
import com.rafid.util.Constants;
import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Partha Chakraborty on 02-Jun-17.
 */
/**
 * <h1>Handles all the task to show report</h1>
 * This class is responsible for serving request related to show report.
 * <p>
 * @author  Partha Chakraborty
 * @version 1.0
 * @since   02-Jun-17
 */
@Controller
public class ReportController {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private RepositoriesRepository repositoriesRepository;
    @Autowired
    private UserRepository userRepository;
    private PieData pieData;
    LineData lineData[];


    /**
     * This method will serve the request with "/goToReport".The main task of the method is serving view with user data.
     *@param courseId is a {@link ModelAttribute} containing  String "courseId" that points  course id of the current course user is requesting.
     *@param httpSession is a {@link HttpSession} type object..
     *@param  model is a {@link Model} class object.
     *@return String log in page or report page url.
     */
    @RequestMapping("/goToReport")
    public String  goToReport(Model model,@ModelAttribute("courseId")String courseId, HttpSession httpSession)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }

            Course course =courseRepository.findByCourseId(Long.parseLong(courseId)).get(0);
            List<Repositories> repositoriesList=repositoriesRepository.findByCourse(course);
            httpSession.setAttribute(Constants.User_List,repositoriesList);
            model.addAttribute(Constants.User_List,repositoriesList);
            model.addAttribute("NO_SELECTION","NO_SELECTION");


        return "report";
    }
    /**
     * This method will serve the request with "/goToReport".The main task of the method is serving view with user data.
     *@param courseId is a {@link ModelAttribute} containing  String "courseId" that points  course id of the current course user is requesting.
     *@param httpSession is a {@link HttpSession} type object..
     *@param  model is a {@link Model} class object.
     *@param  user is a {@link ModelAttribute} containing  String "User_id" that points  user id(enrollees user id ) of the user just clicked.
     *@param redirectAttributes  is a {@link RedirectAttributes} type object.
     *@return String log in page or report page url.
     */
    @RequestMapping("/showReport")
    public String  showReport(Model model, RedirectAttributes redirectAttributes, @ModelAttribute("User_id") String user,@ModelAttribute("courseId")String courseId, HttpSession httpSession)
    {
        String name=(String)httpSession.getAttribute(Constants.userName);
        Course course =courseRepository.findByCourseId(Long.parseLong(courseId)).get(0);
        String collaborators[];
        if(name==null ||(name.length()==0))
        {
            return "redirect:/login";
        }
        if(user==null||user.length()==0)
        {
            List<Repositories> repositoriesList=repositoriesRepository.findByCourse(course);
            httpSession.setAttribute(Constants.User_List,repositoriesList);
            model.addAttribute(Constants.User_List,repositoriesList);
            httpSession.setAttribute(Constants.User_List,repositoriesList);
            model.addAttribute("NO_SELECTION","NO_SELECTION");

        }
        else
        {

            Users repositoryOwner=userRepository.findByUserId(Long.parseLong(user));
            Repositories repository=repositoriesRepository.findByCourseAndUsers(course,repositoryOwner).get(0);
            GitHub gitHub=null;
            try {
                gitHub=GitHub.connectUsingPassword(repositoryOwner.getGitUserId(),repositoryOwner.getGitPassword());
                GHRepository ghRepository=gitHub.getMyself().getRepository(repository.getRepositoryName());
                generateData(ghRepository,model);
                model.addAttribute(Constants.pieDataValue,pieData.getAsJsonString());
                model.addAttribute(Constants.User_List,httpSession.getAttribute(Constants.User_List));
                model.addAttribute(Constants.enrolleesName,repositoryOwner.getFirstName()+" "+repositoryOwner.getLastName());
                model.addAttribute(Constants.lineDataValue,lineData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "report";
    }

    /**
     * This method will serve the request with "/goBackFromReport".The main task of the method  is imitate back button of browser.
     *@param httpSession is a {@link HttpSession} type object.
     *@return String course page url.
     */
    @RequestMapping("/goBackFromReport")
    public String  goBackFromReport(HttpSession httpSession)
    {
        httpSession.removeAttribute(Constants.User_List);
        return "redirect:/course";
    }
    /**
     * This method will collect user data from ghRepository and then set them according to appropriate format in {@link LineData} and {@link PieData} object.
     *@param ghRepository is a {@link GHRepository} type object.
     *@param  model is a {@link Model} class object.
     */
    private void generateData(GHRepository ghRepository,Model model)
    {
        List<GHCommit> l=ghRepository.queryCommits().list().asList();
        List<String> userName= null;

        try {
            userName = nameList(ghRepository.getCollaborators());
            model.addAttribute(Constants.committer,userName);


            double contribute[] = new double[userName.size()];

            double total = 0;
            int index = 0;
            double deletedLine = 0;
            int addedLine;
            String name;
            Date date;
            lineData=new LineData[userName.size()];
            for(int i=0;i<userName.size();i++)
            {
                lineData[i]=new LineData("LineGraph");
            }
            for (int i = 0; i < l.size(); i++) {
                addedLine = l.get(i).getLinesAdded();
                GHUser ghUser=l.get(i).getCommitter();
                if(ghUser!=null)
                {


                    name = ghUser.getLogin();
                    index = userName.indexOf(name);
                    contribute[index] += addedLine;
                    total += addedLine;
                    date = l.get(i).getCommitDate();
                    lineData[index].addX(date);
                    lineData[index].addY(addedLine);
                }

            }
            for(int i=0;i<contribute.length;i++)
            {
                contribute[i]=(contribute[i]/total)*100;
            }

            pieData=new PieData(ReportController.<String>getAsArray(userName,String.class),contribute);


        }catch (IOException e)
        {
            e.printStackTrace();
        }
        return;
    }
    /**
     * This method returns the collaborator list of a repository.
     *@param ghUserGHPersonSet is a {@link GHPersonSet} type object.
     *@return List of String type object.
     */
    private List<String > nameList(GHPersonSet<GHUser> ghUserGHPersonSet)
    {
        List<String> list=new ArrayList<String>();
        Iterator<GHUser> iterator=ghUserGHPersonSet.iterator();
        for(;iterator.hasNext();)
        {
            list.add(iterator.next().getLogin());
        }
        return list;
    }
    /**
     * This method  will return the content of a set or list as an array of object.
     *@param c is a {@link Class}.
     *@param object is a generic {@link Object} .
     *@param  <E>  is the type of object.
     *@return String log in page or report page url.
     */
    private static <E> E[] getAsArray(Object object,Class c)
    {
        if(object instanceof List)
        {
            List<E> list=(List<E>)object;
            E[] temp=(E[]) Array.newInstance(c,list.size());
            for(int i=0;i<list.size();i++)
            {
                temp[i]=list.get(i);
            }
            return  temp;
        }
        else if(object instanceof Set)
        {
            Set<E> set=(Set<E>)object;
            E[] temp=(E[]) Array.newInstance(c,set.size());
            Iterator<E> iterator=set.iterator();
            for(int i=0;iterator.hasNext();i++)
            {
                temp[i]=iterator.next();
            }
            return  temp;
        }
        return  null;
    }
}
