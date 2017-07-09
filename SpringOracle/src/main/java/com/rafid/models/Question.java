package com.rafid.models;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by ab9ma on 5/20/2017.
 */

@Entity
public class Question{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(length = 30,nullable = false)
    private  String content;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Users writtenBy;

    @OneToMany(targetEntity = Answer.class, fetch = FetchType.EAGER)
    private List<Answer> answers;

    @Column(nullable = false)
    Date writtenDate;

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

    public Question() {
    }

    public Question(long id, String content, Users writtenBy, Date writtenDate) {
        this.id = id;
        this.content = content;
        this.writtenBy = writtenBy;
        this.writtenDate = writtenDate;
    }

    public Question(String content, Users writtenBy, Date writtenDate) {
        this.content = content;
        this.writtenBy = writtenBy;
        this.writtenDate = writtenDate;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question(long id, String content) {
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
