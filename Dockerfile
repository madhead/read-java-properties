ARG JAVA=11

FROM openjdk:${JAVA} AS builder
WORKDIR /read-java-properties
COPY . ./
RUN ./gradlew clean install

FROM openjdk:${JAVA}
COPY --from=0 /read-java-properties/build/install /opt/
ENTRYPOINT ["/opt/read-java-properties/bin/read-java-properties"]
