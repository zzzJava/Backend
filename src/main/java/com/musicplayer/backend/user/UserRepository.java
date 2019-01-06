package com.musicplayer.backend.user;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  List<User> findByUsername(String userName);

  List<User> findByUsernameAndPassword(String userName, String passWord);
}