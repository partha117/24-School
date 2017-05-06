package com.rafid.repositories;

import com.rafid.models.Course;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface CourseRepository extends CrudRepository<Course,Long> {
    List<Course> findByCourseId(long courseId);
    List<Course> findByCourseNameAndSubjectAndCourseIntro(String courseName, String subject, String courseIntro);
    List<Course> findAll();
}
