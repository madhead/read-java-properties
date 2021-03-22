ARG JAVA=11

FROM openjdk:${JAVA} AS builder
WORKDIR /read-java-property
COPY . ./
RUN ./gradlew clean install

FROM openjdk:${JAVA}
COPY --from=0 /read-java-property/build/install /opt/
ENTRYPOINT ["/opt/read-java-property/bin/read-java-property"]
