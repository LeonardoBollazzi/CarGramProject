# CarGram

This is a Web application with social media characteristics 

[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)
[![Deploy to Heroku](https://img.shields.io/badge/deploy%20to-Heroku-6762a6.svg?longCache=true)](https://heroku.com/deploy)

#### Contents:
- [Analysis](#analysis)
  - [Scenario](#scenario)
  - [User Stories](#user-stories)
  - [Use Case](#use-case)
- [Design](#design)
  - [Prototype Design](#prototype-design)
  - [Domain Design](#domain-design)
  - [Business Logic Design](#business-logic-design)
  - [Endpoint Design](#endpoint-design)
- [Implementation](#implementation)
  - [Backend Technology](#backend-technology)
  - [Frontend Technology](#frontend-technology)
- [Deployment](#deployment)
- [User Guide](#user-guide)
- [Project Management](#project-management)
  - [Roles](#roles)
  - [Milestones](#milestones)

## Analysis

### Scenario

CarGram is a social media web application for car lovers. Users want to share their experiences of their favorite cars with the rest of the car loving community.
Users desire to enjoy and explore pictures from other users and want to be able to like pleasant content.
Furthermore, users want to know about different car events/festivals that will take place.

### User Stories
1. As a CarGram user, I want to have a Web app so that I can use it on different mobile devices and on desktop computers.
2. As a CarGram user, I want to create an account so that I can get access to CarGram.
3. As a CarGram user, I want to log-in so that I can authenticate myself.
4. As a CarGram user, I want to see a consistent visual appearance so that I can navigate easily, and it looks consistent.
5. As a CarGram user, I want to be able to connect/follow other users
6. As a CarGram user, I want to explore different car events/festivals that will take place in the future.
7. As a CarGram user, I want to explore pictures from users I don't know.
8. As a CarGram user, I want to be able to see pictures from people that I'm connected with.
9. As a CarGram user, I want to be able to see my posted pictures.
10. As a CarGram user, I want to be able to like pictures and events.
11. As a CarGram user, I want to use edit and create views so that I can maintain my business data.
12. As a CarGram user, I want to edit my profile information.

### Use Case
![](images/Use Case.png)

- UC-1 [Login on CarGram]: User can log-in by entering an email address and password. As an extension, new user my register first.
- UC-2 [Register on CarGram]: User can register to get an account (profile) to access the CarGram system.
- UC-3 [Edit profile]: User can edit its profile information when logged in.
- UC-4 [Post]: Logged-in user can post Media/Event.
- UC-5 [Like Media]: logged-in user can like a media post.
- UC-6 [Like Event]: logged-in user can like an event post.

## Design

### Prototype Design

A bootstrap based static prototype has been created by using a prototyping application. 

In this case, the prototype application Bootstrap Studio has been used to create a basic user interface design based on an HTML grid, Bootstrap CSS and JavaScript, including the selection of web fonts and font-based icons.

The assets (HTML, CSS, JavaScript, image and font files) has been exported and will be extended in the later during implementation with jQuery, to build a dynamic website.

### Domain Design

The `ch.fhnw.acrm.data.domain` package contains the following domain objects / entities including getters and setters:

![](images/domain-model.png)

### Business Logic Design

The `ch.fhnw.acrm.business.service` package contains classes of the following business services:

![](images/business-service.png)

### Endpoint Design
**Path**: [`/api/customer`](/api/customer) 

**Method:** `POST`

**Sample Request**  • *Header:* `Content-Type: application/json` • *Body:*

```JSON
{
  "agent": {
    "customers": [
      null
    ],
    "email": "string",
    "id": 0,
    "name": "string",
    "password": "string",
    "remember": "string"
  },
  "email": "string",
  "id": 0,
  "mobile": "string",
  "name": "string"
}
```

• *Optional:* `...`
  
**Success Response**  • *Code:* `200 OK` • *Sample Body:*

```JSON
{
  "agent": {
    "customers": [
      null
    ],
    "email": "string",
    "id": 0,
    "name": "string",
    "password": "string",
    "remember": "string"
  },
  "email": "string",
  "id": 0,
  "mobile": "string",
  "name": "string"
}
```

**Error Response** • *Code:* `404 NOT FOUND`

## Implementation

### Backend Technology
This Web application is relying on [Spring Boot](https://projects.spring.io/spring-boot) and the following dependencies:

- [Spring Boot](https://projects.spring.io/spring-boot)
- [Spring Web](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)
- [Spring Data](https://projects.spring.io/spring-data)
- [Java Persistence API (JPA)](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)
- [H2 Database Engine](https://www.h2database.com)
- [PostgreSQL](https://www.postgresql.org)

To bootstrap the application, the [Spring Initializr](https://start.spring.io/) has been used.

Then the following further dependencies has been added to the project `pom.xml`:

- Swagger and Swagger UI:
```XML
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

- Java HTML Parser and JWT:
```XML
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.14.2</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.2</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-gson</artifactId>
    <version>0.11.2</version>
    <scope>runtime</scope>
</dependency>
```

### Frontend Technology
This Web application is relying on the following frontend technology/libraries:

- jQuery
- Bootstrap

## Deployment
This spring boot has been deployed to Heroku by using a pre-configuration scripts `app.json` and `Procfile`.

## User Guide
The Web application can be accessed over the browser by using the following address: `https://***.herokuapp.com/`. And the Swagger-UI can be access using the specific page: `https://***.herokuapp.com/swagger-ui/`.

## Project Management

### Roles
- All-rounder : [Steven Njugana]()
- All-rounder : [Leonardo Bollazzi]()
- All-rounder : [Chima Obasi]()
- All-rounder : [Damian Schumacher]()
- All-rounder : [Najeem Takiyu-Deen]()

### Milestones
1. **Analysis**: Scenario ideation, use case analysis and user story writing.
2. **Prototype Design**: Creation of Bootstrap static web-design prototype.
3. **Domain Design**: Definition of domain model.
4. **Business Logic and API Design**: Definition of business logic and API.
5. **Data and API Implementation**: Implementation of data access and business logic layers, and API.
6. **Security and Frontend Implementation**: Integration of security framework and frontend realisation.
7. **Deployment**: Deployment of Web application on cloud infrastructure.

#### Maintainer
- [Steven Njugana]()
- [Leonardo Bollazzi]()
- [Chima Obasi]()
- [Damian Schumacher]()
- [Najeem Takiyu-Deen]()

#### License
- [Apache License, Version 2.0](blob/master/LICENSE)