package com.rafid.project;

/**
 * Created by ASUS on 19-May-17.
 */
public class SeeRepository {
    private  String repositoryName;
    private String courseName;

    public SeeRepository(String repositoryName, String courseName) {
        this.repositoryName = repositoryName;
        this.courseName = courseName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
