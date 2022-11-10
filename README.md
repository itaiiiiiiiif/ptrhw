# ptrhw
Pointer Homework Assignment

---------------------------------------------------------------

**versions:**

Apache Maven 3.8.6
Java version: 11.0.17, vendor: Amazon.com Inc., runtime: jdk corretto-11.0.17
Default locale: en_US, platform encoding: Cp1255
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
Vert.x version 4.3.4

---------------------------------------------------------------

**pre-requisities:**

assume you have installed on your machine:
java 11 version , Docker Desktop, mvn-cli (not must), intellij IDE, Postman

---------------------------------------------------------------

**running the app:**
- open terminal and clone the project into your workspace `git clone https://github.com/itaiiiiiiiif/ptrhw.git`
- `cd ptrhw` 
- open the project in your IDE (not must)
- create the application's fat.jar by running `mvn package` in terminal

- run without Docker:
- 1. ` java -jar target/RestVerticleApp-1.0-SNAPSHOT-fat.jar ` 
         **OR** 
- 2. right click and run the application on your IDE

- run with Docker: 
- 1. ` docker build -t vertxdocker . ` (vertxdocker as image name)
- 2. ` docker run -p 8888:8888 vertxdocker`
- you can see the image and the container in your Docker Desktop app
- also in the log you should see that the verticles are up:
![img_9.png](img_9.png)

---------------------------------------------------------------

**Rest Methods available (Test the api):**

Please run Postman app on your machine


1. login 

    Method: POST

    URL: http://localhost:8888/login

    Params:

    example of request body (as json)
    
    `{
    "name" : "Itai@gg.com",
    "pswrd" : "abc123456"
    }`

    **OR**

   `{
   "name": "Levid@gg.com",
   "pswrd" : "abc123456"
   }`

    (password is not really matter for now, the unique email as name do)
    
    for any other email the request should be rejected
    ![img_5.png](img_5.png)
    
    you should get response as below:
      ![img_8.png](img_8.png)

- Please - copy (Ctrl+C) the token that came back in response in order to run the next requests. (without ")

---------------------------------------------------------------

2. get all orders (per user - by token claims)

    Method: GET
    
    URL: http://localhost:8888/api/orders
    
    add to Headers the copied token like below:
    
    `{
    "Authorization" : "Bearer BASE+64+COPIED+TOKEN"
    }`
    
    ![img_3.png](img_3.png)
    you should get response as below:
    ![img_4.png](img_4.png) 
    (running JSON.parse() on the response in the browser console will make it beautify)

---------------------------------------------------------------

3. add new order

    Method: POST
    
    URL: http://localhost:8888/api/orders
    
    Params:
    
    example of request body (as json)

    `{
    "_id" : 1234556,
    "name" : "new order",
    "date": "11/2/2022, 9:29:37 PM"
    }`
    
    add to Headers the copied token like below:
    
    `{
    "Authorization" : "Bearer BASE+64+COPIED+TOKEN"
    }`
    
    ![img_3.png](img_3.png)
    
    you should get the new order joined with the existing ones per the current user like below:
    ![img_6.png](img_6.png)
    (running JSON.parse() on the response in the browser console will make it beautify)


---------------------------------------------------------------


4. logout

    Method: POST
    
    URL: http://localhost:8888/api/logout
    
    add to Headers the copied token like below:
    
    `{
    "Authorization" : "Bearer BASE+64+COPIED+TOKEN"
    }`
    
    ![img_3.png](img_3.png)
    
    Params:
    
    example of request body (as json)
    
   `{
    "name" : "Itai@gg.com"
    }` 
    
    **OR**
    
    `{
    "name": "Levid@gg.com"
    }`
        
    ![img_7.png](img_7.png)
    you should get the result as boolean for logout success

---------------------------------------------------------------
- You can run the 2nd and 3rd requests multiple times in order to see the orders list of the user.
- trying to visit the authorized requests with empty/invalid token should be rejected.



----------------------------------------





TODOS and Problems:

The application is running on one container instead of two for each verticle (RestApi, Order).
In order to run 2 containers I created structure-change branch which wasn't completed.

Need to add postman as dependency for the project and create tasks, instead of running requests manually.

Dates are not handled end 2 end. yet.

Private keys are written in the code.

There are no files and jsons.

Need to switch to use Vertx session for practice. also it's related session cookies.

Many more...

