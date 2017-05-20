package com.rafid.project;

/**
 * Created by ASUS on 19-May-17.
 */
public class AddRepository {

    private  String courseName;
    private String currentRepository;
    private boolean repositoryAvailable;

    public AddRepository(String courseName, String currentRepository, boolean repositoryAvailable) {
        this.courseName = courseName;
        this.currentRepository = currentRepository;
        this.repositoryAvailable = repositoryAvailable;
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
