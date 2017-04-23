package com.rafid.models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ASUS on 19-Apr-17.
 */
@Entity
public class Tutorial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tutorialsId;
    @Column(length = 30,nullable = false)
    private  String tutorialsName;
    @Column(length = 20,nullable = false)
    private  String tutorialsSubject;
    @Column(nullable = false)
    @Lob
    private byte[] tutorialData;
    @ManyToOne(targetEntity = Course.class,fetch = FetchType.EAGER)
   // @JoinColumn(name = "COURSE_ID", nullable = false)
    private Course course;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    // @JoinColumn(name = "USER_ID")
    private Users users;

    public Tutorial(String tutorialsName, String tutorialsSubject, Course course, Users users) {
        this.tutorialsName = tutorialsName;
        this.tutorialsSubject = tutorialsSubject;
        this.course = course;
        this.users = users;
    }

    public Tutorial(Long tutorialsId, String tutorialsName, String tutorialsSubject, byte[] tutorialData) {
        this.tutorialsId = tutorialsId;
        this.tutorialsName = tutorialsName;
        this.tutorialsSubject = tutorialsSubject;
        this.tutorialData = tutorialData;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getTutorialsId() {
        return tutorialsId;
    }

    public void setTutorialsId(Long tutorialsId) {
        this.tutorialsId = tutorialsId;
    }

    public String getTutorialsName() {
        return tutorialsName;
    }

    public void setTutorialsName(String tutorialsName) {
        this.tutorialsName = tutorialsName;
    }

    public String getTutorialsSubject() {
        return tutorialsSubject;
    }

    public void setTutorialsSubject(String tutorialsSubject) {
        this.tutorialsSubject = tutorialsSubject;
    }

    public byte[] getTutorialData() {
        return tutorialData;
    }

    public void setTutorialData(byte[] tutorialData) {
        this.tutorialData = tutorialData;
    }
}
