package org.example.rest.handlers;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import org.example.rest.services.UserService;

public class UserHandler {

  private UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }


  public void doLogout(RoutingContext rc) {
    RequestBody body = rc.body();
    JsonObject user = body.asJsonObject();
    Long userId = user.getLong("userId");
    System.out.println("Logout" + userId);
    JsonObject res = new JsonObject();
    try {
      Boolean success = userService.logout(userId);
      res.put("logout", success);
      rc.response().setStatusMessage("OK").putHeader("Content-Type", "application/json").end(res.encode());
    }
    catch(Exception e) {
      rc.response()
              .setStatusCode(400)
              .setStatusMessage("ValidationException thrown! " + (e.getMessage()))
              .end();
    }

  }

  public void doLogin(RoutingContext rc) {
    RequestBody body = rc.body();
    JsonObject userCredentials = body.asJsonObject();
    Long userId = userCredentials.getLong("userId");
    String pswrd = userCredentials.getString("pswrd");
    System.out.println("Login" + userId + " " + pswrd);
    JsonObject res = new JsonObject();
    try {
      Boolean allowed = userService.login(userId, pswrd);
      res.put("login", allowed);
      rc.response().setStatusMessage("OK").putHeader("Content-Type", "application/json").end(res.encode());
    }
    catch(Exception e) {
      rc.response()
              .setStatusCode(400)
              .setStatusMessage("ValidationException thrown! " + (e.getMessage()))
              .end();
    }
  }
}
