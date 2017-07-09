package com.rafid.repositories;

import com.rafid.models.AnsComment;
import com.rafid.models.QuesComment;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ab9ma on 5/20/2017.
 */
public interface AnsCommentRepository extends CrudRepository<AnsComment, Long> {
}
