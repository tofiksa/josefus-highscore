# josefus-highscore
This is a spring boot application that initially handles all authentication and authorization
for a small game I am working on.

## Written in spring boot
This uses spring-boot-security for authentication and authorization. The application also issues a jwt token.
It also handles all database interactions using jpa and flyway

### To run the app

`mvn clean install && java -jar target/target/josefus-highscore.jar`

To get an overview of all the api endpoints use this url `http://localhost:8080/swagger-ui.html`


