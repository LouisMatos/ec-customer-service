FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /workspace/app

COPY mvnw .
RUN chmod +x mvnw
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Build the app (use package to produce runnable jar in target)
RUN ./mvnw -B -DskipTests package

# Extract dependencies and classes to keep final image minimal
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Use a smaller runtime image for the final image and keep it minimal
FROM eclipse-temurin:21-jre-jammy
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency

# Copy only what's necessary from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# Allow runtime JVM options via JAVA_OPTS and also allow passing args to the Java main
ENV JAVA_OPTS=""

# Entrypoint uses a tiny shell wrapper so JAVA_OPTS and container args ($@) are supported.
# Using exec ensures Java receives signals properly.
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -cp app:app/lib/* br.matosit.customer_service.CustomerServiceApplication \"$@\""]

# Default: no extra args; users can pass JVM options via -e JAVA_OPTS and/or provide app args
CMD [""]

