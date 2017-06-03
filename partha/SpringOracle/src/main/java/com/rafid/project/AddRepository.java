package com.rafid.project;

import org.hibernate.type.UUIDBinaryType;

import java.util.UUID;

/**
 * Created by ASUS on 19-May-17.
 */
public class AddRepository {

    private  String courseName;
    private String currentRepository;
    private boolean repositoryAvailable;
    private String  randomName;
    private  long courseId;

    public AddRepository(String courseName, String currentRepository, boolean repositoryAvailable, long courseId) {
        this.courseName = courseName;
        this.currentRepository = currentRepository;
        this.repositoryAvailable = repositoryAvailable;
        this.randomName = UUID.randomUUID().toString();
        this.courseId = courseId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getRandomName() {
        return randomName;
    }

    public void setRandomName(String randomName) {
        this.randomName = randomName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCurrentRepository() {
        return currentRepository;
    }

    public void setCurrentRepository(String currentRepository) {
        this.currentRepository = currentRepository;
    }

    public boolean isRepositoryAvailable() {
        return repositoryAvailable;
    }

    public void setRepositoryAvailable(boolean repositoryAvailable) {
        this.repositoryAvailable = repositoryAvailable;
    }
}
