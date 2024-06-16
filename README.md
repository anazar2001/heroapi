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