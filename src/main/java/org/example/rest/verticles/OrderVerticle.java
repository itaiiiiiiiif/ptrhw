package org.example.rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.json.JsonObject;

public class OrderVerticle  extends AbstractVerticle {
    private MessageConsumer<String> m_getAllOrdersEvent;
    private MessageConsumer<JsonObject> m_insertOrderEvent;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        EventBus evBus = vertx.eventBus();
        evBus.publish(EventBusTopics.START_EVENT, "OrderVerticle started");
        m_getAllOrdersEvent = evBus.consumer(EventBusTopics.GET_ALL_ORDERS, handler -> {
            System.out.println(handler.body());
            handler.reply("GET_ALL_RESPONSE");
        });

        m_insertOrderEvent = evBus.consumer(EventBusTopics.INSERT_ORDER);
        m_insertOrderEvent.handler(message -> {
            JsonObject json = new JsonObject();//JsonObject.mapFrom(message.body());
            System.out.println(message.body());
            json.put("newOrder", message.body());
            message.reply(json);
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
