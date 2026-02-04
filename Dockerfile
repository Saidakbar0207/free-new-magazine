# ---- 1) BUILD STAGE ----
FROM maven:3.9.8-eclipse-temurin-21 AS build

# Ishchi direktoriyani yaratish
WORKDIR /app

# Faqat pom.xml ni yuklaymiz — dependencelarni cache qilish uchun
COPY pom.xml .

# Dependencelarni yuklab olish (tezlashtirish uchun)
RUN mvn -e -B dependency:go-offline

# Endi kodni yuklaymiz
COPY src ./src

# JAR build qilish (testlarsiz — container uchun)
RUN mvn clean package -DskipTests


# ---- 2) RUN (APPLICATION) STAGE ----
FROM eclipse-temurin:21-jre

WORKDIR /app

# Build bosqichidan jar ni olish
COPY --from=build /app/target/*.jar app.jar

# Spring Boot ilovani ishga tushurish
ENTRYPOINT ["java", "-jar", "app.jar"]