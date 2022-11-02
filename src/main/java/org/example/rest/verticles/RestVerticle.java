package org.example.rest.verticles;

import com.hazelcast.client.impl.clientside.ClientConnectionManagerFactory;
import com.hazelcast.client.impl.clientside.HazelcastClientInstanceImpl;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.rest.handlers.OrderHandler;
import org.example.rest.handlers.UserHandler;
import org.example.rest.repository.UserRepository;
import org.example.rest.services.UserService;

public class RestVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        vertx.eventBus().publish(EventBusTopics.START_EVENT, "RestVerticle started");

        final UserRepository userRepository = new UserRepository(vertx);
        final UserService userService = new UserService(userRepository);
        final UserHandler userHandler = new UserHandler(userService);

        final OrderHandler orderHandler = new OrderHandler(vertx);

        Router router = getRouter(vertx, userHandler, orderHandler);

        vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
            if (http.succeeded()) {
                System.out.println("HTTP server started on port 8888");
                startPromise.complete();
            } else {
                startPromise.fail(http.cause());
            }
        });
    }

    private Router getRouter(Vertx vertx, UserHandler userHandler, OrderHandler orderHandler) {
        Router router = Router.router(vertx);
        router.route("/login").handler(routingContext -> {
            HttpServerResponse response = routingContext.response();
            response
                .putHeader("content-type", "text/html")
                .end("<h1>Hello from my first Vert.x application</h1>");//TODO login template/page
        });

        router.get("/api/orders").handler(orderHandler::getAllOrders);

        router.route(HttpMethod.POST, "/api/orders").handler(BodyHandler.create());
        router.post("/api/orders").handler(orderHandler::addOrder);

        router.route(HttpMethod.POST, "/api/login").handler(BodyHandler.create());
        router.post("/api/login").handler(userHandler::doLogin);

        router.route(HttpMethod.POST, "/api/logout").handler(BodyHandler.create());
        router.post("/api/logout").handler(userHandler::doLogout);

        return router;
    }


    @Override
    public void stop(Promise<Void> startPromise) throws Exception {
        startPromise.complete();
    }
}
