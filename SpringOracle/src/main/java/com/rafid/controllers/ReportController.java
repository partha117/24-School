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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ASUS on 02-Jun-17.
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
