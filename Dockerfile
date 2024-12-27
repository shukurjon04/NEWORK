FROM eclipse-temurin:21-jdk AS build

# 2. Ishlash katalogini sozlash
WORKDIR /app

# 3. Maven dependency fayllarini yuklash va Lombokni yuklashni ta'minlash
COPY pom.xml ./
RUN apt-get update && apt-get install -y maven && mvn dependency:go-offline -B

# 4. Loyiha manba kodini ko‘chirish va yig‘ish
COPY src ./src
RUN mvn clean package -DskipTests

# 5. Faqat runtime uchun mo‘ljallangan Java JRE qatlamini tanlash
FROM eclipse-temurin:21-jre

# 6. Yasalgan .jar faylini ko‘chirish
COPY --from=build /app/target/*.jar app.jar

# 7. Portni ochish
EXPOSE 9090

# 8. Applikatsiyani ishga tushirish
ENTRYPOINT ["java", "-jar", "/app.jar"]