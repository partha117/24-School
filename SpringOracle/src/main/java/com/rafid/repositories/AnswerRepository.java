package com.rafid.repositories;

import com.rafid.models.Answer;
import com.rafid.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ab9ma on 5/20/2017.
 */
public interface AnswerRepository extends CrudRepository<Answer, Long> {
    //List<Answer> findByAnswerOnOrderByWrittenDateAsc(Question question);
}
