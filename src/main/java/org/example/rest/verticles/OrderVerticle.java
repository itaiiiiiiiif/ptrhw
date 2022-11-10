package org.example.rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.example.rest.models.Order;
import org.example.rest.models.User;
import org.example.rest.repository.OrderRepository;
import org.example.rest.services.OrderService;

import java.util.Date;
import java.util.List;

public class OrderVerticle  extends AbstractVerticle {
    private MessageConsumer<String> m_getAllOrdersEvent;
    private MessageConsumer<JsonObject> m_insertOrderEvent;
    private OrderService ordersSvc;
    private OrderRepository ordersRepo;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus evBus = vertx.eventBus();
        System.out.println(getVertx());

        //evBus.publish(EventBusTopics.START_EVENT, "OrderVerticle started");

        ordersRepo = new OrderRepository();
        ordersSvc = new OrderService(ordersRepo);

        m_getAllOrdersEvent = evBus.consumer(EventBusTopics.GET_ALL_ORDERS, handler -> {
            System.out.println(handler.body());
            List<Order> res = ordersSvc.getAll(handler.body());
            final JsonArray jsonArray = new JsonArray();
            for (Order o : res) {
                jsonArray.add(JsonObject.mapFrom(o));
            }
            handler.reply(jsonArray.encodePrettily());
        });

        m_insertOrderEvent = evBus.consumer(EventBusTopics.INSERT_ORDER);
        m_insertOrderEvent.handler(message -> {

            String jsonStr = String.valueOf(message.body());
            JsonObject json = new JsonObject(jsonStr);
            String dateStr = json.getString("date");
            Date date = new Date(dateStr);//TODO use dateFormat
            Long _id = json.getLong("_id");
            String name = json.getString("name");
            final Order order = new Order(_id, name, date);

            JsonObject usr = json.getJsonObject("user");
            User user = new User(usr.getString("name"), "");

            List<Order> res = ordersSvc.insert(user, order);
            final JsonArray jsonArray = new JsonArray();
            for (Order o : res) {//TODO - mapper
                jsonArray.add(JsonObject.mapFrom(o));
            }
            message.reply(jsonArray.encodePrettily());
        });
        startPromise.complete();
    }

    @Override
    public void stop(Promise<Void> startPromise) throws Exception {
        m_getAllOrdersEvent.unregister(ar -> {
            if(ar.succeeded()) {
                startPromise.complete();
            }
            else {
                startPromise.fail(ar.cause());
            }
        });
        m_insertOrderEvent.unregister(ar -> {
            if(ar.succeeded()) {
                startPromise.complete();
            }
            else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
