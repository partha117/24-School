package com.rafid.models;

import javax.persistence.*;

/**
 * Created by ASUS on 17-Apr-17.
 */
@Entity

public class MyUser {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private  Integer id;
    private  String  name;
    private  String  email;

    public MyUser() {
    }

    public MyUser(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
