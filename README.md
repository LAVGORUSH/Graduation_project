[![Codacy Badge](https://app.codacy.com/project/badge/Grade/20b4758d66c94212a2369e03f6c2daf6)](https://www.codacy.com/gh/LAVGORUSH/Graduation_project/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=LAVGORUSH/Graduation_project&amp;utm_campaign=Badge_Grade)
## Graduation project for Topjava

----
#### https://javaops.ru/view/topjava

----
Realisation REST API using Spring Boot,Spring Data JPA, Spring Security, Hibernate, H2, JUnit5, Lombok, Swagger
 **without frontend**.

The task is:

Build a voting system for deciding where to have lunch.

 * 2 types of users: admin and regular users
 * Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 * Menu changes each day (admins do the updates)
 * Users can vote on which restaurant they want to have lunch at
 * Only one vote counted per user
 * If user votes again the same day:
    - If it is before 11:00 we assume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed

Each restaurant provides a new menu each day.

-----------------------------
Technology stack:

Spring Boot; Spring data; Spring security; JPA(Hibernate); H2; Lombok; Maven; Git; Junit 5; Swagger, Java 15.

-----------------------------
For testing REST API you can use:
- **Swagger after deploy - http://localhost:8080/swagger-ui/**

*Use credentials for user{log:user@yandex.ru; pas:password},for admin{log:admin@gmail.com; pas:admin}*
