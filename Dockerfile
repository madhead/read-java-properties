ARG JAVA=11

FROM openjdk:${JAVA}
ADD build/distributions/read-java-property.tar /opt
ENTRYPOINT ["/opt/read-java-property/bin/read-java-property"]

LABEL org.opencontainers.image.source='https://github.com/madhead/read-java-property'
