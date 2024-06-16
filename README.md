# SuperHero API

This is a simple API that allows you to get information about superheroes.

## Prepare the project

1. Please make sure have [Maven](https://maven.apache.org/) and [Docker](https://www.docker.com/) installed and running on your machine.

2. Open a terminal

3. Navigate to your project directory

4. Build the project by running the following commands:

```bash
mvn clean package -DskipTests
```

5. Run the following command to build the application docker image, download the dependencies and start the application:

```bash
docker-compose up --build --force-recreate
```

6. To stop the application run the following command:

```bash
docker-compose stop
```
This will have to be done before running the project tests.

8. To start application without rebuilding it, run the following command:

```bash
docker-compose start
```

7. To run the project unit/integration tests, run the following command:

```bash
mvn test
```

## Testing the SuperHero API

1. Open your brower or use a tool like [Postman](https://www.postman.com/)

2. Navigate/use the following URL:

```
http://<your-host>:8080/superheroes
```

You should see a list of all available SuperHeroes in the database, in JSON format.

3. To get more information on what operations are supported and how to use SuperHero API please look at the [SuperHeroController](src/main/java/com/example/heroapi/controller/SuperHeroController.java) class.

## Assumptions

1. I used PostgreSQL as a database for this project. For both the application and the integration tests. This is because it is always better to test the same database as the one used in production. But for instance h2db and PostgreSQL though have a similar syntax but not exactly the same. And to have different ddl/dml scripts for the application and the tests is not great.

2. I used docker-compose to start the db and also to bring up the integration tests dependencies with the help of TestContainers.

3. There could be added more logging and this logging should ideally use the messages from the messages.properties or other similar place. I started using this approach in [SuperHeroController](src/main/java/com/example/heroapi/controller/SuperHeroController.java).

4. I have added just a few endpoints to the RestController and dependant classes. More endpoints could be added.

5. For testing I used JUnit 5, Mockito and TestContainers.

6. [Flyway](https://flywaydb.org/) was used for the database migrations and version control inside the database.

7. Spring Data JPA were used to interact with the database.

8. Used [Lombok](https://projectlombok.org/) to reduce boilerplate code (getters, setters, constructors, hashcode, equals, etc).