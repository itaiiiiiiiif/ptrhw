package org.example.rest.verticles;

import io.vertx.core.*;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.auth.PubSecKeyOptions;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
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

    private JWTAuth jwtProvider;

    //run in IDE
    public static void main(String[] args) {

        ClusterManager mgr = new HazelcastClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, res -> {
            if (res.succeeded()) {
                Vertx vertx = res.result();
                vertx.deployVerticle(RestVerticle.class, new DeploymentOptions(), ar -> {
                    if(ar.succeeded()) {
                        System.out.println("RestVertical via MAIN running..");
                    }
                });
            } else {
                System.out.println("RestVertical failure " + res.cause());
            }
        });

    }

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

                JWTAuth jwtProvider = JWTAuth.create(vertx, new JWTAuthOptions()
                        .addPubSecKey(new PubSecKeyOptions()
                                .setAlgorithm("RS256")
                                .setBuffer(
                                        "-----BEGIN PUBLIC KEY-----\n" +
                                                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqlQk1tfyCzOEr5ESGZmf\n" +
                                                "q4RvALxMXCyLNF4cSTKXaX0d5/C14QOvgCZDORuMzU4aMJ4yNiT1kMUhXrdZtUeo\n" +
                                                "CHuLRH/H6SIs7MCmfPDoaq2hdJ6d0Qii1W7+eOtissVd6KKV2i6p1MaxstnOH85c\n" +
                                                "bThYLjdsebrUDQutz47bW3WhsJEZx9e8PpNLfRxHtOxil2UdAKFadKmVKZ4K7XGm\n" +
                                                "y79SOlQ+ErCuiuQzi5etbHWuEKyvPTpbXpDOAwygUcPXVTQu4Tha/xCiQUfTle4f\n" +
                                                "1ql64evS/j0JQ31P4g0S+IeX9BDCOtL1uqLHfsPGCsFbVPLXewGttPEpaPsEJ7KI\n" +
                                                "wwIDAQAB\n" +
                                                "-----END PUBLIC KEY-----\n"))
                        .addPubSecKey(new PubSecKeyOptions()
                                .setAlgorithm("RS256")
                                .setBuffer(
                                        "-----BEGIN PRIVATE KEY-----\n" +
                                                "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqVCTW1/ILM4Sv\n" +
                                                "kRIZmZ+rhG8AvExcLIs0XhxJMpdpfR3n8LXhA6+AJkM5G4zNThownjI2JPWQxSFe\n" +
                                                "t1m1R6gIe4tEf8fpIizswKZ88OhqraF0np3RCKLVbv5462KyxV3oopXaLqnUxrGy\n" +
                                                "2c4fzlxtOFguN2x5utQNC63PjttbdaGwkRnH17w+k0t9HEe07GKXZR0AoVp0qZUp\n" +
                                                "ngrtcabLv1I6VD4SsK6K5DOLl61sda4QrK89OltekM4DDKBRw9dVNC7hOFr/EKJB\n" +
                                                "R9OV7h/WqXrh69L+PQlDfU/iDRL4h5f0EMI60vW6osd+w8YKwVtU8td7Aa208Slo\n" +
                                                "+wQnsojDAgMBAAECggEAc/Skv75zgTypb/yLHfJ/yPx3tUB7m6SXgCcxioNuoNoU\n" +
                                                "qfotV+pyaAOkcBRZXst0+PQ5qrKumsgZV6nq4IbP1jaFMTTsm8IwY10j+prnHvjl\n" +
                                                "mccCGXSPpk3R3/AfWSUUMXO+ILLz228JOoEc2npaYeCh55TDvHt8MQVqZosSusfV\n" +
                                                "lbaDC3sRemTbjAu+X4QahkvA/dBvs4U0EERj55weHDC2G3+7pRnCGkeX3ETHyy2p\n" +
                                                "KdD2vu0f6OfEm2gydUswrwV50PBOlTpSH+wgBz8EHIo63U4EvNxUvKylVzQjrNfU\n" +
                                                "9hV6SShfp+iZbPRVR64+cGb5EPF6TW9bM+/MiUWGgQKBgQDanIcbWq8ZOaZR/jcQ\n" +
                                                "dRxCouiyXg1Du9Rj155azptrVxQNdaTII+k/dQmSQBWodsk9brk7SZE/7dwxlny8\n" +
                                                "ASvmvxzDPQLnJDknvONvmpcn9f1NK16QLqPX6EjU/B3UZykOLh/mAWoa9Gc6awG2\n" +
                                                "bbRxZtPVEA4nlgN3qiYZtX8+6wKBgQDHdaacIxUZgku61Und51gTxuoVG+/0XS7A\n" +
                                                "XjTRykiimPwi3iyBPjSv7EGfqovF+zJQRi3WjTqr9CCK9njz4+i9h5jFEbiCAc7a\n" +
                                                "Qe9WslBWyMRH7SAL5Qdlc0AJOIFWKACQlCsQGi4vpaPhaij2WAnNnasYrl4u9SW8\n" +
                                                "7USj+qhXiQKBgQC0Z4XrYfuronKJqXNBhpNqvkuvnoPtyJxuRGqu319MxpCKxvHU\n" +
                                                "JCaBMpFSesYkvto1cyEzNvPBwQX8ega3k3PqOP6Ac+HoY3EzROKfoABrfsmpHEgu\n" +
                                                "Tf7x1wP7l476UKOFyzSRt8sbMWPaxqGkLYZCKyxW4Kf9rQNxhh1pC+lVPwKBgF/S\n" +
                                                "0NlALXnU1AcNQvB9nR3bOUgs5Mm0HqrCV4PWN7EN6EvkCuOIfA1sZlaLu5zAwno4\n" +
                                                "TPs/XEmR/jRkltUsz/qF80nx6n9i2PqYJKC1B825pDqd3AB65mPBy6jiYZh/nU3Y\n" +
                                                "MRwKR9gSLrozozw+LW9/6NvxoUPT+G5cWtagrFFpAoGAEPa5uWzln4fqAGP9od59\n" +
                                                "eCgSq3TS/QfRLy5mj+8ctRKWla7T/XpyeQcEq7Caq/en557/AAerIbLn7TUIP0OB\n" +
                                                "dUb47z9huHy4Dq2x71ujfWqhi8qmmjbnDIZXJ9P7XPGrFtJrqVMba5CyyEbkbopj\n" +
                                                "D6Ro+51shSvqCU3dMp2zENk=\n" +
                                                "-----END PRIVATE KEY-----\n")
                        ));

                userRepository = new UserRepository(vertx);
                userService = new UserService(jwtProvider, userRepository);
                userHandler = new UserHandler(userService);

                orderHandler = new OrderHandler(vertx, jwtProvider, userHandler);

                Router router = getRouter(vertx, userHandler, orderHandler);

                vertx.createHttpServer()
                        .requestHandler(router).listen(8888, http -> {
                    if (http.succeeded()) {
                        System.out.println("HTTP server started on port 8888");
                        startPromise.complete();
                    } else {
                        startPromise.fail(http.cause());
                    }
                });
            } else {
                System.out.println("vertx failure " + res.cause());
            }
        });
    }

    private Router getRouter(Vertx vertx, UserHandler userHandler, OrderHandler orderHandler) {
        Router router = Router.router(vertx);

//        router.route().handler(routingContext -> {
//            HttpServerResponse response = routingContext.response();
//            response
//                .putHeader("content-type", "text/html")
//                .end("<h1>Hello from my first Vert.x application</h1>");
//        });

        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.route(HttpMethod.POST, "/login").handler(BodyHandler.create());
        router.post("/login").handler(userHandler::doLogin);

        router.route("/api").handler(JWTAuthHandler.create(jwtProvider));

        router.get("/api/orders").handler(orderHandler::getAllOrders);

        router.route(HttpMethod.POST, "/api/orders").handler(BodyHandler.create());
        router.post("/api/orders").handler(orderHandler::addOrder);

        router.route(HttpMethod.POST, "/api/logout").handler(BodyHandler.create());
        router.post("/api/logout").handler(userHandler::doLogout);

        return router;
    }


    @Override
    public void stop(Promise<Void> startPromise) throws Exception {
        startPromise.complete();
    }
}
