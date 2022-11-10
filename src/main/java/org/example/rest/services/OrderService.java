package org.example.rest.services;


import io.vertx.core.json.JsonObject;
import org.example.rest.models.Order;
import org.example.rest.models.User;
import org.example.rest.repository.OrderRepository;

import java.util.List;

public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<Order> getAll(User user) {
    return orderRepository.getAll(user);
  }
  public List<Order> getAll(String sub) {
    return orderRepository.getAll(sub);
  }
  public List<Order> insert(User user, Order order) {
    return orderRepository.insert(user, order);
  }

  public List<Order> addOrder(JsonObject body) {
    System.out.println(body);
    return orderRepository.getAll("");
  }
}
