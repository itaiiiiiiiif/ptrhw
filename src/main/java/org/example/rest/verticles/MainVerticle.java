package org.example.rest.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.eventbus.MessageConsumer;

public class MainVerticle  extends AbstractVerticle {

    private MessageConsumer<String> m_startEvent;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        m_startEvent = vertx.eventBus().consumer(EventBusTopics.START_EVENT, handler -> {
            System.out.println("Main Verticle ==>" + handler.body());
        });
        startPromise.complete();
    }

    @Override
    public void stop(Promise<Void> startPromise) throws Exception {
        m_startEvent.unregister(ar -> {
            if(ar.succeeded()) {
                startPromise.complete();
            }
            else {
                startPromise.fail(ar.cause());
            }
        });
    }
}
