# Используйте образ с Java и установленным Maven, например, maven:3.8.3-openjdk-17 или другой подходящий образ.
FROM maven:3.8.3-openjdk-17

# Установите рабочую директорию внутри контейнера
WORKDIR /app

# Копируйте файлы проекта в контейнер
COPY pom.xml .
COPY src ./src

# Выполните сборку проекта
RUN mvn dependency:go-offline compile package

EXPOSE 8080

CMD java -jar target/CanIGoJnVacationService-0.0.1-SNAPSHOT.jar
