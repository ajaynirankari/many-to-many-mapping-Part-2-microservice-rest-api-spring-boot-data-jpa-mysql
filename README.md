# many-to-many-mapping-Part-2-microservice-rest-api-spring-boot-data-jpa-mysql

MySQL Database setup
--------------------

Login on MySQL
Execute the following SQL query to create new database, db user and assign access permission
```
DROP DATABASE IF EXISTS manytomany;
CREATE DATABASE manytomany;
CREATE USER 'dbUser'@'%' IDENTIFIED BY 'dbUser';
GRANT ALL ON manytomany.* TO 'dbUser'@'%';

```

File - src/main/resources/application.properties
----
```
spring.datasource.url=jdbc:mysql://localhost:3306/manytomany
spring.datasource.username=dbUser
spring.datasource.password=dbUser
spring.jpa.hibernate.ddl-auto=update
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.show-sql=true
```

Steps to Run Application
------------------------
1. Open Git Bash
2. git clone https://github.com/ajaynirankari/many-to-many-mapping-Part-2-microservice-rest-api-spring-boot-data-jpa-mysql
3. cd many-to-many-mapping-Part-2-microservice-rest-api-spring-boot-data-jpa-mysql
4. mvn spring-boot:run

Steps to Test Application
-------------------------
1. Open Git Bash
2. curl -v http://localhost:8080/students | jq
```
$ curl -v http://localhost:8080/students | jq
*   Trying 127.0.0.1:8080...
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
  0     0    0     0    0     0      0      0 --:--:-- --:--:-- --:--:--     0* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /students HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.78.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Sat, 18 Nov 2023 06:55:44 GMT
<
{ [228 bytes data]
100   222    0   222    0     0    894      0 --:--:-- --:--:-- --:--:--   898
* Connection #0 to host localhost left intact
[
  {
    "id": 1,
    "name": "Anish",
    "courses": [
      {
        "id": 1,
        "name": "Java"
      }
    ]
  },
  {
    "id": 2,
    "name": "Rehan",
    "courses": [
      {
        "id": 1,
        "name": "Java"
      },
      {
        "id": 3,
        "name": "C++"
      }
    ]
  },
  {
    "id": 3,
    "name": "Samith",
    "courses": [
      {
        "id": 2,
        "name": "SQL"
      },
      {
        "id": 3,
        "name": "C++"
      }
    ]
  }
]

```

Test REST API via Swagger
-------------------------
URL: http://localhost:8080/swagger-ui/index.html
![image](https://github.com/ajaynirankari/many-to-many-mapping-Part-2-microservice-rest-api-spring-boot-data-jpa-mysql/assets/26870634/4324ed05-b704-4433-a5e0-50ec1cc2c5bf)

Configuration for the Swagger-UI
--------------------------------
Reference Link: https://springdoc.org/

Add below dependency in pom.xml
```
<dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.2.0</version>
</dependency>
```
