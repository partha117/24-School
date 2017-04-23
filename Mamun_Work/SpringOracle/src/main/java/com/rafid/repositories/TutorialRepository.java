package com.rafid.repositories;

import com.rafid.models.Tutorial;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface TutorialRepository extends CrudRepository<Tutorial,Long> {
}
