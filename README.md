E-Voting Spring Boot Application
Introduction
This Spring Boot application is designed to manage and facilitate an electronic voting system. It includes features for managing voters, elections, candidates, and votes, as well as compiling election results.

Prerequisites
JDK 1.8 or later
Maven 3.2+
MySQL Server 5.7 or later
Setting Up the Database
Create the Database
Run the following SQL commands to create the e_voting database and its tables:

sql
Copy code
CREATE DATABASE e_voting
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE e_voting;

-- Create tables
-- Refer to provided SQL script for table creation
Database Configuration
Update the application.properties file in the Spring Boot project to point to your MySQL instance:

properties
Copy code
spring.datasource.url=jdbc:mysql://localhost:3306/e_voting?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=your_username
spring.datasource.password=your_password

# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
Replace your_username and your_password with your MySQL credentials.

Running the Application
Build the Application
Navigate to the root directory of the project and run:

bash
Copy code
mvn clean install
Run the Application
After successfully building the project, run the following command:

bash
Copy code
java -jar target/e-voting-0.0.1-SNAPSHOT.war
Alternatively, you can run the application directly using Maven:

bash
Copy code
mvn spring-boot:run
Accessing the Application
Once the application is running, you can access it at:

arduino
Copy code
http://localhost:8080
(Adjust the port according to your application.properties configuration if necessary.)
