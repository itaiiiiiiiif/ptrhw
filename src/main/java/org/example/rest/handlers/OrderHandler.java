package org.example.rest.handlers;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.MessageCodec;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RequestBody;
import io.vertx.ext.web.RoutingContext;
import org.example.rest.models.Order;
import org.example.rest.services.OrderService;
import org.example.rest.verticles.EventBusTopics;

import java.util.Date;

public class OrderHandler {

  private OrderService orderService;
  private Vertx vertx;

  public OrderHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  private Order mapRequestBodyToOrder(RoutingContext rc) {

    Order order = new Order();

    try {
      RequestBody body = rc.body();
      JsonObject json = body.asJsonObject();
      String dateStr = json.getString("date");
      Date date = new Date(dateStr);//TODO use dateFormat
      Long _id = json.getLong("_id");
      String name = json.getString("name");
      order = new Order(_id, name, date); //rc.body().asJsonObject().mapTo(Order.class);
    } catch (IllegalArgumentException ex) {
      onErrorResponse(rc, 400, ex);
    }
    return order;
  }

  private void onSuccessResponse(RoutingContext rc, int status, Object object) {
    rc.response()
      .setStatusCode(status)
      .putHeader("Content-Type", "application/json")
      .end(Json.encodePrettily(object));
  }

  private void onErrorResponse(RoutingContext rc, int status, Throwable throwable) {
    final JsonObject error = new JsonObject().put("error", throwable.getMessage());

    rc.response()
      .setStatusCode(status)
      .putHeader("Content-Type", "application/json")
      .end(Json.encodePrettily(error));
  }

  public void getAllOrders(RoutingContext rc) {
    System.out.println("getAllOrders");
    vertx.eventBus().request(EventBusTopics.GET_ALL_ORDERS, "getOrders", ar -> {
        if(ar.succeeded()) {
          onSuccessResponse(rc, 200, ar.result().body());
        }
        else {
          onErrorResponse(rc, 400, new Exception("failure in get all orders"));
        }
    });
  }

  public void addOrder(RoutingContext rc) {
    System.out.println("addOrder");

    RequestBody body = rc.body();
    JsonObject json = body.asJsonObject();
    String dateStr = json.getString("date");
    Date date = new Date(dateStr);//TODO use dateFormat
    Long _id = json.getLong("_id");
    String name = json.getString("name");
    final Order order = new Order(_id, name, date); //rc.body().asJsonObject().mapTo(Order.class);

    // final Order order = mapRequestBodyToOrder(rc);
    System.out.println("order id: " + order.get_id());

    try {
      //TODO if order is not empty

      vertx.eventBus().request(EventBusTopics.INSERT_ORDER, json, ar -> {
        if(ar.succeeded()) {
          onSuccessResponse(rc, 200, ar.result().body());
        }
        else {
          onErrorResponse(rc, 400, new Exception("failure in add order"));
        }
      });
    }
    catch(Exception e) {
      e.printStackTrace();
    }


  }
}
