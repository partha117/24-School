package com.rafid.repositories;

import com.rafid.models.Course;
import com.rafid.models.Tutorial;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface TutorialRepository extends CrudRepository<Tutorial,Long> {
    List<Tutorial> findByTutorialsId(long tutorialsId);
    List<Tutorial> findByCourse(Course course);
}
