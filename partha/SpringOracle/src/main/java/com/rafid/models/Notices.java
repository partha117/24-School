package com.rafid.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ASUS on 19-Apr-17.
 */
@Entity
public class Notices implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeId;
    private int viewNumber;
    @Column(nullable = false)
    private String noticeText;
    @Column(length = 20,nullable = false)
    private String topic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date noticeDate;
    @ManyToOne(targetEntity = Course.class,fetch = FetchType.EAGER)
   // @JoinColumn(name = "COURSE_ID", nullable = false)
    private Course course;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    // @JoinColumn(name = "USER_ID")
    private Users users;

    public Notices(int viewNumber, String noticeText, String topic, Date noticeDate, Course course, Users users) {
        this.viewNumber = viewNumber;
        this.noticeText = noticeText;
        this.topic = topic;
        this.noticeDate = noticeDate;
        this.course = course;
        this.users = users;
    }

    public Notices(int viewNumber, String noticeText, String topic, Date noticeDate) {
        this.viewNumber = viewNumber;
        this.noticeText = noticeText;
        this.topic = topic;
        this.noticeDate = noticeDate;
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

    public int getViewNumber() {
        return viewNumber;
    }

    public void setViewNumber(int viewNumber) {
        this.viewNumber = viewNumber;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public Date getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(Date noticeDate) {
        this.noticeDate = noticeDate;
    }
}
