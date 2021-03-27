ARG JAVA=11

FROM openjdk:${JAVA} AS builder
WORKDIR /read-java-properties
COPY . ./
RUN ./gradlew clean install

FROM openjdk:${JAVA}
COPY --from=0 /read-java-properties/build/install /opt/
ENTRYPOINT ["/opt/read-java-properties/bin/read-java-properties"]

LABEL org.opencontainers.image.authors="madhead <siarhei.krukau@gmail.com>"
LABEL org.opencontainers.image.source="https://github.com/madhead/read-java-property"
LABEL org.opencontainers.image.licenses="MIT"
LABEL org.opencontainers.image.title="read-java-properties"
LABEL org.opencontainers.image.description="GitHub Action to read values from Java's .properties files"
