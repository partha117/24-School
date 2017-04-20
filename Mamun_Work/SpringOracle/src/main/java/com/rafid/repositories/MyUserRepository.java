package com.rafid.repositories;

import com.rafid.models.MyUser;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ASUS on 17-Apr-17.
 */
public interface MyUserRepository extends CrudRepository<MyUser, Long> {

}