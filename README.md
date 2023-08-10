# Calculator App Backend

This is the backend for the Calculator App. It provides APIs for performing operations, managing records, and user authentication.

## Table of Contents

- [Prerequisites](#prerequisites)
- [Setup](#setup)
- [Environment Variables](#environment-variables)
- [Database Setup](#database-setup)
- [Scripts](#scripts)
- [Running the Application](#running-the-application)

## Prerequisites

- Java Development Kit (JDK) 17
- IntelliJ IDE
- PostgreSQL database
- Your favorite API testing tool (e.g., Postman)

## Setup

1. Clone this repository to your local machine.
2. Use IntelliJ to build and run the project.

## Environment Variables

Before running the application, you need to set the following environment variables:

DB_URL: The URL of your PostgreSQL database.
DB_USERNAME: The username for accessing the database.
DB_PASSWORD: The password for the database user.
SECRET_KEY: Secret key used for JWT token generation.

## Database Setup

Create a PostgreSQL database with the name calculator.
Import the database schema using the provided SQL scripts. The scripts can be found in src/main/resources/scripts.

## Scripts

insert-operations.sql: Insert predefined operations into the database.
insert-users.sql: Insert users with hashed passwords.
insert-user-roles.sql: Insert user roles.

## Running the Application

Make sure the PostgreSQL database is up and running.
Set the required environment variables in your development environment.
Run the application

## Note

The default port is set to 8080.
The guest user's password is welcome123.
You can create a user with a hashed password using bcrypt online.
