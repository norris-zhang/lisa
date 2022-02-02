# lisa
Lisa Class Management System

### Dev Run
* Check out the project
* $ mvn clean package
* $ mvn spring-boot:run -Dspring.profiles.active=dev

### Development Database
There is a file-based H2 Database. When packaging or running, the Database is initialised and test data is inserted.\
`spring.jpa.hibernate.ddl-auto=create-drop` determines that the tables are re-created every time the program is run.
`com.norriszhang.lisa.bootstrap.InitDb.run()` method is inserting test data when the program is started.

### Production Run
$ mvn package -Dspring.profiles.active=prod\
$ java -jar -Dspring.profiles.active=prod target/lisa-0.0.1-SNAPSHOT.jar\
$ nohup java -jar -Dspring.profiles.active=prod target/lisa-0.0.1-SNAPSHOT.jar > ~/catalina.out 2>&1 &

---
---
### APIs

#### API name: /login
#### Request:
GET http://localhost:8080/login?username=lisa&password=password
#### Response:
```json
{
  "id": 123,
  "loginUsername": "lisa",
  "displayName": "Lisa Yao",
  "role": "TEACHER",
  "token": "xxxxxxxxxx"
}
```
---
#### API name: /classes
#### Request:
GET http://localhost:8080/classes \
Authorization \
Bearer Token: xxxxxxxxxx

#### Response:
```json
{
  "classes": [
    {
      "id": 123,
      "name": "Saturday 1.30pm 5yr",
      "description": "",
      "dayOfWeek": "SAT",
      "startTime": [13, 30],
      "duration": 90
    },
    {
      "id": 456,
      "name": "Sunday 1.30pm 5yr",
      "description": "",
      "dayOfWeek": "SUN",
      "startTime": [13, 30],
      "duration": 90
    }
  ]
}
```
