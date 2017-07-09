package com.rafid.models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ab9ma on 5/20/2017.
 */
@Entity
@Table(name="QuesComments")
public class QuesComment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(length = 30,nullable = false)
    private  String content;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Users writtenBy;

    @Column(nullable = false)
    Date writtenDate;

    @ManyToOne(targetEntity = Question.class,fetch = FetchType.EAGER)
    private Question commentOn;


    public QuesComment(long id, String content, Users writtenBy, Date writtenDate, Question commentOn) {
        this.id = id;
        this.content = content;
        this.writtenBy = writtenBy;
        this.writtenDate = writtenDate;
        this.commentOn = commentOn;
    }

    public Question getCommentOn() {
        return commentOn;
    }

    public void setCommentOn(Question commentOn) {
        this.commentOn = commentOn;
    }

    public Users getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(Users writtenBy) {
        this.writtenBy = writtenBy;
    }

    public Date getWrittenDate() {
        return writtenDate;
    }

    public void setWrittenDate(Date writtenDate) {
        this.writtenDate = writtenDate;
    }

    public QuesComment(long id, String content, Users writtenBy, Date writtenDate) {
        this.id = id;
        this.content = content;
        this.writtenBy = writtenBy;
        this.writtenDate = writtenDate;
    }
    public QuesComment(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
