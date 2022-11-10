package org.example.rest.services;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.JWTOptions;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.web.Session;
import org.example.rest.models.User;
import org.example.rest.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicReference;

public class UserService {
  private final UserRepository userRepository;
  private JWTAuth jwtProvider;

  public UserService(JWTAuth jwtProvider, UserRepository userRepository) {
    this.jwtProvider = jwtProvider;
    this.userRepository = userRepository;
  }

  public void login(RoutingContext rc, String userName, String pswrd) {
    Session session = rc.session().get(userName);
    User user = userRepository.login(rc, userName, pswrd);
    if(user != null) {
      String token = jwtProvider.generateToken(
              new JsonObject().put("sub", user.getName()),
              new JWTOptions()
                      .setSubject(user.getName())
                      .setExpiresInMinutes(60)
                      .setAlgorithm("RS256")
                      .setIssuer(user.getName())
                      .addAudience(user.getName()));

      user.setToken(token);
      rc.setUser(user);
      rc.session().put(token, user);
      rc.response()
              .setStatusCode(201)
              .putHeader("content-type", "application/json; charset=UTF-8")
              .end(Json.encodePrettily(user));
    }
    else {
      rc.response()
              .setStatusCode(401)
              .putHeader("content-type", "application/json; charset=UTF-8")
              .end(Json.encodePrettily(null));
    }
  }
  public Boolean logout(RoutingContext rc, String token) {
    AtomicReference<Boolean> res = new AtomicReference<>(false);
    String[] tokens = token.split(" ");

    try {
      jwtProvider.authenticate(new JsonObject().put("token", tokens[1]))
              .onSuccess(user -> {
                System.out.println("User: " + user.principal());
                res.set(userRepository.logout(rc, token));
              })
              .onFailure(err -> {
                System.out.println("authenticate failure logout false");
              });

      //TODO - fix - with Bearer prefix it causes invalid JWT format
//      jwtProvider.authenticate(new JsonObject().put("token", token))
//              .onSuccess(user -> {
//                System.out.println("User: " + user.principal());
//                res.set(userRepository.logout(rc, token));
//              })
//              .onFailure(err -> {
//                System.out.println("authenticate failure logout false");
//              });

    }
    catch(Exception e ) {
      e.printStackTrace();
      return res.get();
    }
    return res.get();
  }

  public User getUser(String sub) {
    return userRepository.getUsersMap().get(sub);
  }
}
