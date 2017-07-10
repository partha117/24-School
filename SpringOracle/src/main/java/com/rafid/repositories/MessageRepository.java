package com.rafid.repositories;

import com.rafid.models.Message;
import com.rafid.models.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by ASUS on 21-Apr-17.
 */
public interface MessageRepository extends CrudRepository<Message,Long> {
    List<Message> findBySender(Users sender);
    List<Message> findAll();
    List<Message> findByReceiver(Users receiver);
}
