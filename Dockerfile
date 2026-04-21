# =========================
# STAGE 1 - BUILD
# =========================
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw

# Baixa dependências para aproveitar cache
RUN ./mvnw dependency:go-offline

COPY src ./src

# Gera o jar sem testes
RUN ./mvnw clean package -DskipTests

# Remove jar plain, se existir
RUN rm -f target/*-plain.jar

# =========================
# STAGE 2 - RUNTIME
# =========================
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

ENV TZ=America/Fortaleza
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError"

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]