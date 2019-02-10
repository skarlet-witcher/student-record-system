cd cloudRegistry
start cmd /K run.bat
cd ../cloudAuth
start cmd /K java -jar "build/libs/cloud-auth-0.0.1-SNAPSHOT.war" --spring.profiles.active=dev
cd ../cloudGateway
start cmd /K java -jar "build/libs/cloud-gateway-0.0.1-SNAPSHOT.war" --spring.profiles.active=dev,no-liquibase
cd ..