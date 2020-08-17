## A Story of Passionate Software Engineer
You have an idea develop a website which allow users to store and share GPS track online (similar to http://www.trackprofiler.com/track/index). After discussion with your team, they helped you to came up with some mock-up files. 
Front-end side will be developed by another team member. You are the only one who is going to be in charge of the backend service development.

Because you are so excited to show the idea to your CEO, you decided to reduce the scope and focus one developing 1 WS API with three endpoints:

- An endpoint that allow users to upload "gpx" file and store mandatory information such as "metadata, waypoint, track" 
- An endpoint to return a list of "Latest track" from our users
- An endpoint to allow users to view details of their gpx file

Although this is a prototype version, but you are a professional software engineer. You don't allow yourself to code without a System Diagram or Workflow Diagram, or produce "dirty-code" and code without Unit Tests. Additionally, since this is a fairly small and simple project, you are not allowed to use the Lombok library.

Once your have completed your solution, please upload them to Github.

This is all you have right now: 

- https://en.wikipedia.org/wiki/GPS_Exchange_Format
- Mock-up files
- A sample gpx file
- A passionate heart, if you don't like the given mock-up files, feel free to change and show your CEO a better version
- Your team is a big fan of "Spring IO" tech stack, so they prefers you use Sprint Boot as a starting point
- An in-memory database is enough for this moment (H2)

# gpsTracking

This application was generated using JHipster 6.10.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.10.1](https://www.jhipster.tech/documentation-archive/v6.10.1).

## Development

I using Jhipster to generate this project with Spring boot, JPA, Spring Security - JWT, Tracking Audit by user_id, Migrate database by Liquibase.

To initialize, we need create database:

CREATE DATABASE IF NOT EXISTS `gpsTracking` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

Liquibase will init user data by csv files on folder /resources/config/liquibase/data/**

username and password with role:
+ Role Admin: . system || system  -----   admin || admin
+ Role User:    . user || user

Using file gpsTracking.postman_collection.json to import postman collection api.

API List:

+ /api/authenticate: to get jwt to authenticate user.

+ /api/trips/upload: to upload gpx file for the trip.
+ /api/trips: list all trip of logged User with pageable.
+ /api/trips/:id: get detail trip by id with reponse JSON.
+ /api/trips/:id/xml: get detail trip by id with reponse XML.

+ /api/management/trips: list all trips on system for Role Admin with pageable.

To start your application in the dev profile, run:
```
./mvnw
```
For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

### Packaging as jar
To build the final jar and optimize the gpsTracking application for production, run:
```
./mvnw -Pprod clean verify

```
To ensure everything worked, run:
```
java -jar target/*.jar

```
Refer to [Using JHipster in production][] for more details.

### Packaging as war
To package your application as a war in order to deploy it to an application server, run:
```
./mvnw -Pprod,war clean verify
```

## Testing

To launch your application's tests, run:
```
./mvnw verify
```

For more information, refer to the [Running tests page][].


