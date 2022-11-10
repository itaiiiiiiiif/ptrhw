package org.example.rest;

public class App {
    public static void main(String[] args) {

//        ClusterManager mgr = new HazelcastClusterManager();
//        VertxOptions options = new VertxOptions().setClusterManager(mgr);
//        Vertx.clusteredVertx(options, res -> {
//            if (res.succeeded()) {
//                Vertx vertx = res.result();
//                vertx.deployVerticle(MainVerticle.class, new DeploymentOptions(), ar1 -> {
//                    if(ar1.succeeded()) {
//                        vertx.deployVerticle(OrderVerticle.class, new DeploymentOptions(), ar2 -> {
//                            if(ar2.succeeded()) {
//                                vertx.deployVerticle(RestVerticle.class, new DeploymentOptions(), ar3 -> {
//                                    if(ar3.succeeded())
//                                        System.out.println("all verticles are up");
//                                });
//                            }
//                        });
//                    }
//                });
//            } else {
//                // failed!
//            }
//        });
    }
}