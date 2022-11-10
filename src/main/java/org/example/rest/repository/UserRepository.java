package org.example.rest.repository;


import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import org.example.rest.models.User;

import java.util.HashMap;

public class UserRepository {

  private HashMap<String, User> users = new HashMap<>();
  private Vertx vertx;
  private EventBus evBus;
  public UserRepository(Vertx vertx) {
    this.vertx = vertx;
    this.evBus = this.vertx.eventBus();
    createSomeData();
  }

  public void createSomeData() {
    User levi = new User("levid@gg.com", "abc123");
    User itai = new User("Itai@gg.com", "abc123");
    users.put(levi.getName(), levi);
    users.put(itai.getName(), itai);
  }

  public User login(RoutingContext rc, String userName, String pswrd) {
    //User user = new User(userName, pswrd);
    Session session = rc.session().get(userName);
    User existInDB = users.get(userName);

    io.vertx.ext.auth.User user = rc.user();

    if(existInDB != null || session != null) {
       System.out.println("session != null" + session);
       return existInDB;//user logged in case
    }
    else return null;
  }

  public Boolean logout(RoutingContext rc, String token) {
    try {
      rc.session().remove(token);
      return true;
    }
    catch(Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  public HashMap<String, User> getUsersMap() {
    return users;
  }
}
