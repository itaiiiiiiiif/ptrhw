package org.example.rest.models;



import io.vertx.codegen.annotations.Nullable;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.AuthProvider;
import io.vertx.ext.auth.authorization.Authorization;
import io.vertx.ext.auth.authorization.Authorizations;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class User implements io.vertx.ext.auth.User {
  private static final AtomicInteger COUNTER = new AtomicInteger();
  private final int id;
  private String name;
  private String pswrd;
  //for demonstration:
  private String token;

  public User(String name, String pswrd) {
    this.id = COUNTER.getAndIncrement();
    this.name = name;
    this.pswrd = pswrd;
    this.token = new String("");
  }

  @Override
  public @Nullable String subject() {
    return io.vertx.ext.auth.User.super.subject();
  }

  @Override
  public JsonObject attributes() {
    return null;
  }

  @Override
  public boolean expired() {
    return io.vertx.ext.auth.User.super.expired();
  }

  @Override
  public boolean expired(int leeway) {
    return io.vertx.ext.auth.User.super.expired(leeway);
  }

  @Override
  public <T> @Nullable T get(String key) {
    return io.vertx.ext.auth.User.super.get(key);
  }

  @Override
  public boolean containsKey(String key) {
    return io.vertx.ext.auth.User.super.containsKey(key);
  }

  @Override
  public Authorizations authorizations() {
    return io.vertx.ext.auth.User.super.authorizations();
  }

  @Override
  public io.vertx.ext.auth.User isAuthorized(Authorization authorization, Handler<AsyncResult<Boolean>> handler) {
    return null;
  }

  @Override
  public io.vertx.ext.auth.User isAuthorized(String authority, Handler<AsyncResult<Boolean>> resultHandler) {
    return io.vertx.ext.auth.User.super.isAuthorized(authority, resultHandler);
  }

  @Override
  public Future<Boolean> isAuthorized(Authorization authority) {
    return io.vertx.ext.auth.User.super.isAuthorized(authority);
  }

  @Override
  public Future<Boolean> isAuthorized(String authority) {
    return io.vertx.ext.auth.User.super.isAuthorized(authority);
  }

  @Override
  public io.vertx.ext.auth.User clearCache() {
    return io.vertx.ext.auth.User.super.clearCache();
  }

  @Override
  public JsonObject principal() {
    return null;
  }

  @Override
  public void setAuthProvider(AuthProvider authProvider) {

  }

  @Override
  public io.vertx.ext.auth.User merge(io.vertx.ext.auth.User user) {
    return null;
  }
}
