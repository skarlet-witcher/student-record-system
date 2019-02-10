## Introduction
jhipster born integrating with best practices from first line engineers.

now jhipster are only best with Jdk8. Please pay attention.

## Start
refer to the document of [**jhipster**](https://www.jhipster.tech/development/).

1. set up the dev environment by following instruction above.

2. run cloudRegistry/cloudAuth/cloudGateway before others: 

   - Method 1: run `run infrastructure.bat` in root folder. you may see error like *can't find cloudauth*, just wait seconds for cloudauth finishing register 
   - Method 2: run `cloudRegistry/run.bat`, `cloudAuth/gradlew.bat`, `cloudGateway/run.bat` in order.
  
3. run microservice locally with active profile: 
   `spring.profiles.active=dev`, if with 'no-liquibase', then service can't create the db tables.
   
- API doc in `localhost:xxxx/v2/api-docs` and gateway.
- H2 database in `localhost:xxxx/h2-console`.
- the frontend (Angular) located in `cloudGateway\src\main\webapp\app`
- one cross-microservice query example in `svcUnivCourseModule.c.o.s.u.c.m.client.StudentFeignClient`

-------
`TIPS` another way is to run in docker compose.

## Test Account

- admin:admin
- user:user

## Some Port

- 8080 gateway
- 8761 registry
- 9999 cloudAuth
- 8081 svcStudent
- 8082 svcUnivCourseModule
