FROM adoptopenjdk/openjdk11 AS build-stage
WORKDIR /usr/src/app
ARG USER_API_PORT
ENV USER_API_PORT=${USER_API_PORT}
COPY . .
RUN ./gradlew assemble

FROM adoptopenjdk/openjdk11 AS production-stage
COPY --from=build-stage /usr/src/app/build/libs/pooper-user-api-*-all.jar ./
EXPOSE 8080
CMD ["java", "-jar", "pooper-user-api-*-all.jar"]