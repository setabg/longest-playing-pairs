
# Longest Playing Pairs

## Overview
This application identifies football player pairs from the same team who have played together the longest during a match. It excludes any playtime starting from minute 0 and handles cases where the end time of play (ToMinute) is null, treating it as 90 minutes.

## Key Features
Team and Match Constraints: Compares players from the same team who played in the same match.
Exclusion of Playtime Starting from 0: Focuses on playtime that starts after minute 0.
Handling Null End Times: Records with a null ToMinute are treated as if they ended at 90 minutes, the standard length of a football match.
CSV Data Import: Loads player, team, match, and record data from CSV files without using external libraries for CSV parsing.
Date Format Support: Handles multiple date formats for flexible data input.

## Algorithm
Load Data: Import data from CSV files into a PostgreSQL database.
Filter Records: Exclude records where playtime starts from minute 0.
Handle Null End Times: Treat null ToMinute values as 90 minutes.
Compute Overlaps: For each match and team:
Identify pairs of players.
Calculate the total overlap in playtime for each pair of players, ensuring they are from the same team and played in the same match.
Consider only overlapping periods where both players are on the field simultaneously.

## CSV File Format
Ensure that your CSV files have the correct format:

- **players.csv**: Player information (ID, Team Number, Position, Full Name), Import on http://localhost:8082/players/import
- **teams.csv**: Team information (ID, Name, Manager, Group Name), import on http://localhost:8082/teams/import
- **matches.csv**: Match information, import on http://localhost:8082/matches/import
- **records.csv**: Records of player playtime in matches (Player ID, Match ID, From Minutes, To Minutes), import on http://localhost:8082/records/import

## Running the Application
1. **Database Setup**: Ensure you have PostgreSQL installed and running. Create a database named `football_db`:
   ```bash
   createdb football_db
   ```
2. **Configure Application Properties**: Make sure the connection details in `src/main/resources/application.properties` are correct:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/football_db
   spring.datasource.username=postgres
   spring.datasource.password=******
   ```

3. **Place CSV Files**: Add your CSV files (for players, teams, matches, and records) to `src/main/resources/data/` folder.

4. **Run the Application**: Start the Spring Boot application using Maven:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**: Open your browser or use a tool like Postman to access the results at:
   ```
   http://localhost:8082/api/pairs/longest
   ```

## Technologies Used
- **Java 21**
- **Spring Boot 3.3.3**
- **PostgreSQL** for the database.
- **Apache Commons CSV** for parsing CSV files.
- **Hibernate** and **JPA** for ORM (Object-Relational Mapping).

## Testing
- The project includes **H2** as an in-memory database for testing purposes.
- You can run tests using:
   ```bash
   mvn test
   ```

## Notes
- The application uses Java 21, Spring Boot 3.3.3, and PostgreSQL.
- Make sure to adjust the PostgreSQL configuration (`username`, `password`, and `url`) according to your environment in `application.properties`.

---
