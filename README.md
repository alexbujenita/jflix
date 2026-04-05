# jFlix



## Useful commands

psql into the database:

```shell
docker exec -ti postgres_db psql -U postgres
```

Flyway commands:

```shell
# Migrate the database locally
mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/postgres -Dflyway.user=postgres -Dflyway.password=t00r

# If DB_URL, DB_USER and DB_PASSWORD are set in the environment, you can omit the parameters:
mvn flyway:migrate
```
