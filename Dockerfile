# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /src

# Public mirror only. Maven Central is slow from this network; the file has no credentials.
RUN cat > /opt/maven-settings.xml <<'EOF'
<settings>
  <mirrors>
    <mirror>
      <id>aliyun-public</id>
      <url>https://maven.aliyun.com/repository/public</url>
      <mirrorOf>*</mirrorOf>
    </mirror>
  </mirrors>
</settings>
EOF

COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -B -s /opt/maven-settings.xml -DskipTests dependency:go-offline
COPY src ./src
RUN --mount=type=cache,target=/root/.m2/repository \
    mvn -B -s /opt/maven-settings.xml -DskipTests package

FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
RUN addgroup --system --gid 1001 appgroup && \
    adduser --system --uid 1001 --ingroup appgroup --no-create-home appuser
COPY --link --from=build --chown=1001:1001 /src/target/drift-bottle-1.0.0.jar app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
