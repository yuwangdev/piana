### PiazzaAnalytics High-performance Data-as-a-Service (DaaS) API Framework 

#### Major Tech Specifications
- MongoDB 3.2.7 
- Docker as the micro-service container 
- Spring Boot 1.4.1 + Java 1.8 
- JMS asynchronous message component 
- Guava Cache for database reading and network communication
- RestFul data service APIs 
- Apache Tomcat 8.5 server 

#### How to build and run 
- modify the external configuration file pianaEnv_dev.config at the root path
- ensure the database "piana" in MongoDB has been set up 
- ensure the correctness of database configuration in resources/application.properties 
- on the command line interface, under the project root, enter: ./gradlew build 
- if the project is to be deployed in Docker, enter: ./gradlew buildDocker 
- to run the application, on the command line interface, enter: java -jar build/docker/demo-0.0.1-SNAPSHOT.jar

#### Documents on the code
- please find the /build/docs directory 

#### File Structure
- /build/classes: all compiled classes of java
- /build/docker: docker final project and .jar runnable project 
- /build/docs: documents 
- /build/report: test reports 
- /build/test-results: test result 
- build.gradle: gradle build script
- /daily_diff_data: any change of Piazza daily data will be stored here 
- /document: some screenshots 
- src: source code 





