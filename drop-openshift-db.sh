psql -h $POSTGRESQL_SERVICE_HOST -p $POSTGRESQL_SERVICE_PORT -U postgres task-api < drop-task-api-db.ddl
