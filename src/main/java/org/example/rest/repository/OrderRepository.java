package org.example.rest.repository;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import org.example.rest.models.Order;

import java.util.concurrent.atomic.AtomicReference;

public class OrderRepository {

  private Vertx vertx;
  private EventBus evBus;
  public OrderRepository(Vertx vertx) {
    this.vertx = vertx;
    this.evBus = this.vertx.eventBus();
  }

  public JsonObject getAll() {

      AtomicReference<JsonObject> query = new AtomicReference<>(new JsonObject());
      this.evBus.<JsonObject>request("/getAll", null, ar -> {
        if(ar.succeeded()) {
          query.set(ar.result().body());
        }
        else{
          ar.cause().printStackTrace();
        }
      });
      return query.get();
  }

//  public  void handle(AsyncResult<Message<JsonObject>> result) {
//    //request get fails before consumer don't send reply within 30 seconds
//    if(result.succeeded()){
//      System.out.println("Answer: "+Thread.currentThread().getName());
//      System.out.println(result.result().body());
//    } else{
//      result.cause().printStackTrace();
//    }
//  }

  public Order insert(Order order) {

    return order;
  }
}
