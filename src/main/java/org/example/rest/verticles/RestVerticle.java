package org.example.rest.verticles;

import com.hazelcast.client.impl.clientside.ClientConnectionManagerFactory;
import com.hazelcast.client.impl.clientside.HazelcastClientInstanceImpl;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import org.example.rest.handlers.OrderHandler;
import org.example.rest.handlers.UserHandler;
import org.example.rest.repository.UserRepository;
import org.example.rest.services.UserService;

public class RestVerticle extends AbstractVerticle {

    private UserRepository userRepository;
    private UserService userService;
    private UserHandler userHandler;
    private  OrderHandler orderHandler;
    @Override
    public void start(Promise<Void> startPromise) throws Exception {

        //vertx.eventBus().publish(EventBusTopics.START_EVENT, "RestVerticle started");

        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(OrderVerticle.class, new DeploymentOptions(), ar -> {
                    if(ar.succeeded()) {
                        System.out.println("order vertical is up");
                    }
                });

                userRepository = new UserRepository(vertx);
                userService = new UserService(userRepository);
                userHandler = new UserHandler(userService);

                orderHandler = new OrderHandler(vertx);

                Router router = getRouter(vertx, userHandler, orderHandler);

                vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
                    if (http.succeeded()) {
                        System.out.println("HTTP server started on port 8888");
                        startPromise.complete();
                    } else {
                        startPromise.fail(http.cause());
                    }
                });


            } else {
                System.out.println("order vertical failure " + res.cause());
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
