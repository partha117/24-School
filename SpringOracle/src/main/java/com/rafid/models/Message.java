package com.rafid.models;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ab9ma on 5/20/2017.
 */

@Entity
public class Message implements Comparable{
    @Autowired

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column( length = 500, nullable = false)
    private  String content;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Users sender;
    @ManyToOne(targetEntity = Users.class,fetch = FetchType.EAGER)
    private Users receiver;
    @Column
    private char seen;

    @Column(nullable = false)
    Date sentDate;
    public Message(long id, String content, Users sender, Users receiver, Date sentDate) {
        this.id = id;
        this.content = content;
        this.sentDate = sentDate;
        seen = 'F';
    }
    public Message(long id, String content) {
        this.id = id;
        this.content = content;
        this.sentDate = new Date();
        seen = 'F';
    }
    public Message(long id){
        this.id = id;
        this.sentDate = new Date();
        seen = 'F';
    }
    public Message(){
        this.sentDate = new Date();
        seen = 'F';
    }
    public Message(String content) {
        this.content = content;
        this.sentDate = new Date();
        seen = 'F';
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

    public Users getSender() {
        return sender;
    }

    public void setSender(Users sender) {
        this.sender = sender;
    }

    public Users getReceiver() {
        return receiver;
    }

    public void setReceiver(Users receiver) {
        this.receiver = receiver;
    }

    public char getSeen() {
        return seen;
    }

    public void setSeen(char seen) {
        this.seen = seen;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Message){
            return ((Message) o).sentDate.compareTo(sentDate);
        }
        return -1;
    }
}
