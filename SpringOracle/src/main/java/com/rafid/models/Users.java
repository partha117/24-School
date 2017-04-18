package com.rafid.models;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by ASUS on 4/18/2017.
 */
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    String userId;
    @Column(unique = true, columnDefinition = "VARCHAR(100)", nullable = false)
    String userName;
    @Column(columnDefinition = "VARCHAR(200)")
    String firstName;
    @Column(columnDefinition = "VARCHAR(200)")
    String lastName;
    @Column(nullable = false)
    Date birthDate;
    @Column(columnDefinition = "VARCHAR(200)")
    String email;
    @Column(columnDefinition = "VARCHAR(200)")
    String password;
    @Column(columnDefinition = "VARCHAR(7)")
    String gender;
    @Column(columnDefinition = "VARCHAR(40)")
    String profession;
    @Column(columnDefinition = "VARCHAR(100)")
    String country;
    @Column(columnDefinition = "VARCHAR(100)")
    String state;
    @Column(columnDefinition = "VARCHAR(100)")
    String city;
    @Column(columnDefinition = "VARCHAR(100)")
    String zipCode;
    @Column(columnDefinition = "NUMBER(10)")
    int rating;
    Blob profilePic;

    public Users(){

    }

    public Users(String userName, String firstName, String lastName, Date birthDate, String email, String password, String gender, String profession, String country, String state, String city, String zipCode, Blob profilePic) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.profession = profession;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;

        this.profilePic = profilePic;
    }

    public Users(String userName, String firstName, String lastName, Date birthDate, String email, String password, String gender, String profession, String country, String state, String city, String zipCode) {
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.profession = profession;
        this.country = country;
        this.state = state;
        this.city = city;
        this.zipCode = zipCode;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Blob getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Blob profilePic) {
        this.profilePic = profilePic;
    }
}
