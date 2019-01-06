package com.musicplayer.backend.user;

import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class UserController {

  private static final Logger log = LoggerFactory.getLogger(Application.class);
  private static UserRepository userRepository = null;

  @Autowired
  public UserController(ApplicationContext appContext) {
    Repositories repositories = new Repositories(appContext);
    Optional<Object> repository = repositories.getRepositoryFor(User.class);
    repository.ifPresent(o -> userRepository = (UserRepository) o);
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String register(@RequestParam String username, @RequestParam String password) {
    User user = new User(username, password);
    try {
      userRepository.save(user);
    } catch (Exception e) {
      log.warn("create failed " + user.toString());
      return "1";
    }
    log.info("create " + user.toString());
    return "0";
  }

  @PostMapping("/login")
  @ResponseStatus(HttpStatus.OK)
  public @ResponseBody
  String login(@RequestParam String username, @RequestParam String password) {
    List<User> list = userRepository.findByUsernameAndPassword(username, password);
    if (list.size() != 0) {
      log.info("login success " + username);
      return "0";
    }
    log.info("login failed " + username);
    return "1";
  }
}
