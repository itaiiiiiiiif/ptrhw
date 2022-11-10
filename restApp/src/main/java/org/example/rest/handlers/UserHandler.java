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
    String token = rc.request().headers().get("Authorization");
    JsonObject user = body.asJsonObject();
    //String token = user.getString("token");
    System.out.println("Logout" + token);
    JsonObject res = new JsonObject();
    try {
      Boolean success = userService.logout(rc, token);
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
    String userName = userCredentials.getString("name");
    String pswrd = userCredentials.getString("pswrd");
    System.out.println("Login" + userName + " " + pswrd);
    JsonObject res = new JsonObject();
    try {

      userService.login(rc, userName, pswrd);

//      Boolean allowed = userService.login(rc, userId, pswrd);
//      res.put("login", allowed);
//      rc.response().setStatusMessage("OK").putHeader("Content-Type", "application/json").end(res.encode());
    }
    catch(Exception e) {
      rc.response()
              .setStatusCode(400)
              .setStatusMessage("ValidationException thrown! " + (e.getMessage()))
              .end();
    }
  }
}
