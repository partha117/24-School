package com.rafid.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ASUS on 19-Apr-17.
 */
@Entity
public class Course implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COURSE_ID",unique = true,nullable =false)
    private Long courseId;
    @Column(length = 30,nullable = false)
    private String courseName;
    @Column(length = 30,nullable = false)
    private String  subject;
  //  @OneToMany(targetEntity = Notices.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   // private Set<Notices>notices=new HashSet<>(0);
   // @OneToMany(targetEntity = Tutorial.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   // private Set<Tutorial>tutorials=new HashSet<>(0);
  //  @OneToMany(targetEntity = Repositories.class,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
   // private Set<Repositories>repositories=new HashSet<>(0);
    @ManyToMany(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Set<Users> usersSet=new HashSet<>(0);
    @ManyToMany(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Set<Users> instructors=new HashSet<>(0);


    public Course(Long courseId, String courseName, String subject) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.subject = subject;
    }

    /*public Set<Repositories> getRepositories() {
        return repositories;
    }

    public void setRepositories(Set<Repositories> repositories) {
        this.repositories = repositories;
    }

    public Set<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(Set<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }


    public Set<Notices> getNotices() {
        return notices;
    }

    public void setNotices(Set<Notices> notices) {
        this.notices = notices;
    }*/

    public Set<Users> getUsersSet() {
        return usersSet;
    }

    public void setUsersSet(Set<Users> usersSet) {
        this.usersSet = usersSet;
    }

    public Set<Users> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<Users> instructors) {
        this.instructors = instructors;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
