package com.rafid.repositories;

import com.rafid.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ab9ma on 5/20/2017.
 */
public interface QuestionRepository extends CrudRepository<Question,Long> {


}

