# ptrhw
Pointer Homework Assignment

pre-requisites:
- you have mvn installed
- you have docker desktop installed
- java 11

running the app:
- clone the project into your workspace `git clone https://github.com/itaiiiiiiiif/ptrhw.git`
- `cd prthw` 
- create the fat.jar `mvn package`
- run without Docker  ` java -jar target/RestVerticleApp-1.0-SNAPSHOT-fat.jar` **OR** right click and run the application on your IDE (I used intellij)
- run with Docker ` docker build -t vertxdocker .`
- `docker run -p 8888:8888 vertxdocker`


Rest Methods available (I used Postman):

   Desc: login and create user session
   Method: POST
   URL: http://localhost:8888/api/login
   Params:
   example of request body (as json)
   {
      "userId" : 1234556,
      "pswrd" : "abc123456"
   }

   ![img.png](img.png)


   Desc: logout and clear user session   
   Method: POST
   URL: http://localhost:8888/api/logout
   Params:
   example of request body (as json)
   {
      "userId" : 1234556
   }
   
   ![image](https://user-images.githubusercontent.com/83422637/199584430-282d6b6e-3a87-44eb-988e-32bc90145385.png)




   Desc: insert new order
   Method: POST
   URL: http://localhost:8888/api/orders
   Params:
   example of request body (as json)
   {
      "_id" : 1234556,
      "name" : "new order",
      "date": "11/2/2022, 9:29:37 PM"
   }
   
   ![image](https://user-images.githubusercontent.com/83422637/199584998-8b7cd4ba-2ef6-49ee-9133-9eaf8c56f9e6.png)


   Desc: get all orders (suppose to be per user)
   Method: GET
   URL: http://localhost:8888/api/orders
   

TODOS and Problems:

running the app using docker does not run the OrdersVerticle of vertx (running using java command it works), so actually what is left is to create another docker file for OrderVerticle and a docker-compose.yml file to build it and run the containers for each verticle.
Also, the data is not really exist and there are no files to read from and write to. Only the main architecture is implemented.

