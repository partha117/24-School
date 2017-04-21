package com.rafid.repositories;

import com.rafid.models.Course;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface CourseRepository extends CrudRepository<Course,Long> {
}
