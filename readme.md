##Introduction
jhipster born integrating with best practices from first line engineers.

## Start
refer to the document of [**jhipster**](https://www.jhipster.tech/development/).

- set up the dev environment by following instruction above.
- run cloudRegistry before others: `.\mvnw -Pdev,webpack`(linux) or `mvnw -Pdev,webpack`(cmd in windows)
- run microservice locally with active profile: spring.profiles.active=dev,no-liquibase
- API doc in `localhost:xxxx/v2/api-docs`
- H2 database in `localhost:xxxx/h2-console`
- the frontend (Angular) located in `cloudGateway\src\main\webapp\app`
- one cross-microservice query example in `svcUnivCourseModule.c.o.s.u.c.m.client.StudentFeignClient`

-------
`TIPS` another way is to run in docker compose.

## Some Port

- 8080 gateway
- 8761 registry
- 9999 cloudAuth
- 8081 svcStudent
- 8082 svcUnivCourseModule
