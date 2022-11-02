# ptrhw
Pointer Homework Assignment

pre-requisites:
- you have mvn cli installed
- you have docker desktop installed
- java 11

running the app:
- clone the project into your workspace `git clone https://github.com/itaiiiiiiiif/ptrhw.git`
- `cd prthw` 
- create the fat.jar of maven `mvn package`
- run without Docker  ` java -jar target/RestVerticleApp-1.0-SNAPSHOT-fat.jar`
- with Docker ` docker build -t vertxdocker .`
- `docker run -p 8888:8888 vertxdocker`
- `http://localhost:8888`


![img.png](img.png)

![img_1.png](img_1.png)

