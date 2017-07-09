package com.rafid.repositories;

import com.rafid.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ASUS on 4/18/2017.
 */
public interface UserRepository extends CrudRepository<Users, Long> {
    List<Users> findByEmailAndPassword(String email, String password);
    List<Users> findByUserNameAndPassword(String userName, String password);
    List<Users> findByUserName(String userName);
    Users findByUserId(long userId);
    List<Users> findByUserNameContainsOrFirstNameContainsOrLastNameContainsAllIgnoreCase(String uName, String fName, String lName);
}
