package com.rafid.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by ASUS on 4/18/2017.
 */
@Entity
public class Users implements Serializable{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long userId;
    @Column(unique = true, columnDefinition = "VARCHAR(100)", nullable = false)
    private String userName;
    @Column(columnDefinition = "VARCHAR(200)", nullable = false)
    private String firstName;
    @Column(columnDefinition = "VARCHAR(200)", nullable = false)
    private String lastName;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = true)                                                     ///changed it, date of birth can be null-------
    private Date birthDate;
    @Column(unique = true, columnDefinition = "VARCHAR(200)", nullable = false) ///changed it-mamun... email should be unique
    private String email;
    @Column(columnDefinition = "VARCHAR(200)", nullable = false)
    private String password;
    @Column(columnDefinition = "CHAR(1)")   ///changed it -mamun... gender will be stored as 'M', 'F', or 'O'
    private String gender;
    @Column(columnDefinition = "VARCHAR(40)")
    private String profession;
    @Column(columnDefinition = "VARCHAR(100)")
    private String country;
    @Column(columnDefinition = "VARCHAR(100)")
    private String state;
    @Column(columnDefinition = "VARCHAR(100)")
    private String city;
    @Column(columnDefinition = "VARCHAR(100)")
    private String zipCode;
    @Column(columnDefinition = "NUMBER(10)")
    int rating;
    @Column(name = "GIT_ACCOUNT_ID", columnDefinition = "VARCHAR(100)")
    String gitUserId;

    @Lob
    private byte[] profilePic;

    public Users(){

    }

    public String getGitUserId() {
        return gitUserId;
    }

    public void setGitUserId(String gitUserId) {
        this.gitUserId = gitUserId;
    }


    public Users(long userId, String userName, String firstName, String lastName, String email, String password) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Users(long userId, String userName, String firstName, String lastName, Date birthDate, String email, String password, String gender, String profession, String country, String state, String city, String zipCode, byte[] profilePic) {
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
        this.userId = userId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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

    public byte[] getProfilePic() {return profilePic;}

    public void setProfilePic(byte[] profilePic) {
        this.profilePic = profilePic;
    }
}
