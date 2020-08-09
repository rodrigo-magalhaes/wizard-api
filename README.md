# Wizard API
API to manage new wizards in the world of Harry Potter.
As user you can create, update, find by id or by house 
and delete wizards

The house information is validated with a call to 
[potterapi](www.potterapi.com) to ensure that the house
exists in this universe

##Technologies 
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) - language used to develop
- [Maven 3.6](https://maven.apache.org/) - dependency manager used to build the project
- [lombok](https://projectlombok.org/) - code generation 
- [Spring Boot 2.3](https://spring.io/projects/spring-boot) - used to regulate version dependencies, 
embed server application and add some configurations by default
- [H2](https://www.h2database.com/html/main.html) - in memory database
- [Spring Data](https://spring.io/projects/spring-data) - used to manage database connection
- [Spring Cloud](https://spring.io/projects/spring-cloud) - used to implement circuit braker
- [Spring Web](https://spring.io/guides/gs/serving-web-content/) - used to receive http requests and responses
- [Jackson](https://github.com/FasterXML/jackson) - used to transform from json to object and vice versa
- [JUnit 5](https://junit.org/junit5/) - used to develop tests
- [Intellij](https://www.jetbrains.com/idea/) - ide used to code

##Get the project running
To those who want to play with or build upon, follow these steps:
```shell
//clone the repository
git clone https://github.com/rodrigo-magalhaes/wizard-api

//go to project directory
cd "where the project was downloaded"

//build
maven clean install

//run
mvn spring-boot:run
```

##API
Make a request to this endpoint to start 
```shell
/api/characters
```
using this json as exemple
```shell
{
    "name": "Harry Potter",
    "role": "student",
    "school": "Hogwarts School of Witchcraft and Wizardry",
    "house": "5a05e2b252f721a3cf2ea33f",
    "patronus": "stag"
}
```
**name** and **house** are mandatory, so dont forget to pass it 
- Create a new character, passing the json above (as exemple)
```shell
POST /api/characters
```
- Read a single character or all characters
```shell
//list all characters in the database
GET /api/characters
//get the character that has the id
GET /api/characters/{id}
//list all characters by house
GET /api/characters?house=xyz
```
- Update a single character
```shell
//id is mandatory, so it must be provided on the json
PUT /api/characters
```
- Delete a single character
```shell
DELETE /api/characters/{id}
```

##Circuit Breaker
Hystrix is used as circuit breaker
```java
@HystrixCommand(fallbackMethod = "houseError", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })
    public House getHouseByApiId(String houseApiId) {
        //code ...
    }
```
**fallbackMethod** is used to call another method in case anything goes wrong.It was needed to pass a property using the 
annotation **@HystrixProperty** to specify the timeout for a request. By default it uses another thread to obtain the response, 
so the application waits for 5 seconds before transfer the execution to the method in fallbackMethod 


##Cache
All successful requests generate a new **house** on H2 database table. Before each request, a call to the database is done
to guarantee that a request has to be made. So after the first request, all others that search for the same house api id
will be retrieved from the database and not from the [potterapi](www.potterapi.com) anymore.