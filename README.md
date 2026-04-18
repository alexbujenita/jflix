# jFlix



## Run the application locally:

1. Make sure you have Docker installed and running on your machine.
2. Run `docker-compose up` in the root directory of the project.
3. Run the db migrations with Flyway
4. Generate the PEM keys for JWT authentication and place them in `src/main/resources/keys/` (see commands below).
5. Start the application with the `local` profile active. You can do this by setting the `SPRING_PROFILES_ACTIVE` environment variable to `local` or by passing the `-Dspring.profiles.active=local` argument when running the application.

## Useful commands

Create keys

```shell
openssl genpkey -algorithm RSA -out src/main/resources/keys/jwt-private.pem -pkeyopt rsa_keygen_bits:4096
openssl rsa -pubout -in src/main/resources/keys/jwt-private.pem -out src/main/resources/keys/jwt-public.pem
```

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
