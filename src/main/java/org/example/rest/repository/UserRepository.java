package org.example.rest.repository;


import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

public class UserRepository {

  private Vertx vertx;
  private EventBus evBus;
  public UserRepository(Vertx vertx) {
    this.vertx = vertx;
    this.evBus = this.vertx.eventBus();
  }

  public Boolean login(Long userId, String pswrd) {

    return true;
  }

  public Boolean logout(Long userId) {
    return true;
  }
}
