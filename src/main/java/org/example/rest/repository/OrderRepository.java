package org.example.rest.repository;

import org.example.rest.models.Order;
import org.example.rest.models.User;

import java.util.*;

public class OrderRepository {

//  private Vertx vertx;
//  private EventBus evBus;
  private HashMap<String, List<Order>> ordersMap;
  public OrderRepository() {
    this.ordersMap = new HashMap<String, List<Order>>();
    createSomeData();
  }

  public void createSomeData() {
    User levi = new User("levid@gg.com", "abc123");
    User itai = new User("Itai@gg.com", "abc123");
    Order itaiOrder = new Order(1,"order of itai", new Date());
    Order leviOrder = new Order(2,"order of levi", new Date());
    List<Order> mockOrdersItai = new ArrayList<Order>();
    List<Order> mockOrdersLevi = new ArrayList<Order>();
    mockOrdersItai.add(itaiOrder);
    mockOrdersLevi.add(leviOrder);
    ordersMap.put(levi.getName(), mockOrdersLevi);
    ordersMap.put(itai.getName(), mockOrdersItai);
  }

  public List<Order> getAll(User user) {
      List<Order> res = new ArrayList<Order>();
      if(!ordersMap.isEmpty()) {
          res = ordersMap.get(user.getName());
      }
      return res;
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

  public List<Order> insert(User user, Order order) {
    List<Order> oldList;
    List<Order> newList;
    if(!ordersMap.isEmpty()) {
      oldList = ordersMap.get(user.getName());
      newList = oldList == null ? new ArrayList<Order>() : new ArrayList<Order>(oldList);
      newList.add(order);
      if(oldList != null) {//replace
        ordersMap.replace(user.getName(), oldList, newList);
      }
      else ordersMap.put(user.getName(), newList);//new
    }
    return ordersMap.get(user.getName());
  }

  public List<Order> getAll(String sub) {
    List<Order> res = new ArrayList<Order>();
    if(!ordersMap.isEmpty()) {
      res = ordersMap.get(sub);
    }
    return res;
  }
}
