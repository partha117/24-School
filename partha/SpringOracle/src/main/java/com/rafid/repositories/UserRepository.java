package com.rafid.repositories;

import com.rafid.models.Users;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ASUS on 4/18/2017.
 */
public interface UserRepository extends CrudRepository<Users, Long> {
}
