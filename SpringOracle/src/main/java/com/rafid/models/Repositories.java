package com.rafid.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ASUS on 19-Apr-17.
 */
@Entity
public class Repositories implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Long repositoryId;
    @Column(length = 30,nullable = false)
    private  String repositoryName;
    @Column(nullable = false)
    private String  repositoryLink;
    @ManyToOne(targetEntity = Course.class,fetch = FetchType.EAGER)
   // @JoinColumn(name = "COURSE_ID")
    private Course course;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
   // @JoinColumn(name = "USER_ID")
    private Users users;

    public Repositories(){

    }

    public Repositories(String repositoryName, String repositoryLink, Course course, Users users) {
        this.repositoryName = repositoryName;
        this.repositoryLink = repositoryLink;
        this.course = course;
        this.users = users;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

/*    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }*/

    public Long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getRepositoryLink() {
        return repositoryLink;
    }

    public void setRepositoryLink(String repositoryLink) {
        this.repositoryLink = repositoryLink;
    }
}
