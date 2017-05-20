package com.rafid.repositories;

import com.rafid.models.Course;
import com.rafid.models.Repositories;
import com.rafid.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface RepositoriesRepository extends CrudRepository<Repositories,Long> {


    List<Repositories> findByCourseAndUsers(Course course, Users users);
}

