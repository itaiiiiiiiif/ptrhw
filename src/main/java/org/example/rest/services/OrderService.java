package org.example.rest.services;


import io.vertx.core.json.JsonObject;
import org.example.rest.models.Order;
import org.example.rest.repository.OrderRepository;

public class OrderService {

  private final OrderRepository orderRepository;

  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public JsonObject getAll() {
    return orderRepository.getAll();
  }
  public Order insert(Order order) {
    return orderRepository.insert(order);
  }
}
