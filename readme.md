<h1 align="center">
    learning-graphql-client
</h1>

    The intention of this project is to publish a REST service by consuming some values from a public graphql api. This project is intend to use a REST client or any other library to establish the connectivity.

For downloading the latest schema, please use the following command
```shell
./gradlew downloadApolloSchema \
  --endpoint="https://rickandmortyapi.com/graphql" \
  --schema="src/main/graphql/com/rick/morty/schema.json"

```

Generated sources will be present inside `build/sources` directory, we may need to add that to sources.



#### Swagger UI
Please check at http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

#### Reference
https://www.apollographql.com/docs/android/
https://moonhighway.com/public-graphql-apis/
https://graphql.org/code/#java-kotlin
https://github.com/k0kubun/graphql-query-builder
https://github.com/leangen/graphql-spqr

