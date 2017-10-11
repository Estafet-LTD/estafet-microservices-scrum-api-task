mvn clean install -P local
cp target/estafet-microservices-scrum-api-task-*.war $WILDFLY_INSTALL/standalone/deployments
