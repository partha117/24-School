package com.rafid.repositories;

import com.rafid.models.Course;
import com.rafid.models.Notices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ASUS on 21-Apr-17.
 */
public interface NoticesRepository extends CrudRepository<Notices,Long> {
    List<Notices> findByCourse(Course course);
    List<Notices> findByNoticeId(long noticeId);
}
