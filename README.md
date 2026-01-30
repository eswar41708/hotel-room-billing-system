# Hotel Room Inventory & Time-Based Billing Backend

## Overview
This project is a Java-based backend system for managing hotel
room inventory, customer check-in/check-out, and time-based
billing using PostgreSQL and JDBC.

It is designed for local/offline use by hotel counter staff.

## Features
- View room availability
- Customer check-in system
- Customer check-out with automatic billing
- PostgreSQL database integration
- Menu-driven console interface

## Technologies Used
- Java
- PostgreSQL
- JDBC
- Git

## Database Setup
Create the following tables in PostgreSQL:

rooms, customers, bookings

(Refer to project SQL script.)

## How to Run
1. Install Java and PostgreSQL
2. Create `hotel_db` and tables
3. Update DB password in `DBConnection.java`
4. Compile and run:

```bash
javac -cp ".;lib/postgresql-42.x.x.jar" *.java
java -cp ".;lib/postgresql-42.x.x.jar" MainApp
