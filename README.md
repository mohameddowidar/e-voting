# E-Voting Spring Boot Application

## Introduction
This Spring Boot application is designed to manage and facilitate an electronic voting system. It includes features for managing voters, elections, candidates, and votes, as well as compiling election results.

## Prerequisites
- JDK 1.8 or later
- Maven 3.2+
- MySQL Server 5.7 or later

## Setting Up the Database

### Create the Database
Run the following SQL commands to create the `e_voting` database and its tables:

```sql
CREATE DATABASE e_voting
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE e_voting;

CREATE DATABASE e_voting
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

-- Use the new schema
USE e_voting;

CREATE TABLE Voter (
    Voter_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255),
    Date_Of_Birth DATE,
    Address TEXT,
    Email VARCHAR(255),
    Phone_Number VARCHAR(20),
    National_ID VARCHAR(50),
    Age INT,
    Face_Biometric_Data LONGBLOB,
    Photo BLOB,
    Created_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    Updated_Date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE Election (
    Election_ID INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255),
    Description TEXT,
    Start_Date DATE,
    End_Date DATE,
    Election_Type VARCHAR(50),
    Year YEAR,
    Created_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    Updated_Date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE Candidate (
    Candidate_ID INT AUTO_INCREMENT PRIMARY KEY,
    Symbol_Name VARCHAR(255),
    Biography TEXT,
    Affiliated_Party VARCHAR(100),
    Election_ID INT,
    Voter_ID INT,
    Symbol BLOB,
    Created_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    Updated_Date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Election_ID) REFERENCES Election(Election_ID)
);

CREATE TABLE Vote (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Voter_ID INT,
    Candidate_ID INT,
    Election_ID INT,
    Time_Stamp DATETIME DEFAULT CURRENT_TIMESTAMP,
    Created_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
    Updated_Date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (Voter_ID) REFERENCES Voter(Voter_ID),
    FOREIGN KEY (Candidate_ID) REFERENCES Candidate(Candidate_ID),
    FOREIGN KEY (Election_ID) REFERENCES Election(Election_ID)
);

CREATE TABLE ElectionResults (
    ResultID INT AUTO_INCREMENT PRIMARY KEY,
    ElectionID INT,
    CandidateID INT,
    TotalVotes INT,
    CreatedDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    UpdatedDate DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (ElectionID) REFERENCES Election(ElectionID),
    FOREIGN KEY (CandidateID) REFERENCES Candidate(CandidateID)
);





### Database Configuration

spring.datasource.url=jdbc:mysql://localhost:3306/e_voting?useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
spring.datasource.username=your_username
spring.datasource.password=your_password

# Hibernate properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect


## Build the Application

mvn clean install

## Running the Application
java -jar target/e-voting-0.0.1-SNAPSHOT.war

### Accessing the Application
http://localhost:9090/e-voting/login.xhtml
