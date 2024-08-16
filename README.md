# User Management API

## Overview

The User Management API is a RESTful service designed for managing user information and authentication. It supports operations for creating, retrieving, updating, and deleting user profiles and includes functionalities for secure user registration, login, and password recovery.

## Features

- **User Management:** Create, read, update, and delete user profiles.
- **Authentication:** Secure login, user registration, and password recovery.
- **Error Handling:** Detailed error messages and validation for better user experience.
- **Email Notifications:** Automated emails for user registration and password recovery.
- **API Documentation:** Interactive API documentation using Swagger.

## Technologies Used

- **Backend Framework:** Spring Boot
- **Programming Language:** Java
- **Database:** MySQL
- **Authentication:** Basic authentication (no JWT in the current implementation)
- **API Documentation:** Swagger
- **Build Tool:** Maven

## API Endpoints

- **User Management:**

    - `POST /user-api/save` - Register a new user
    - `GET /user-api/report` - List all users
    - `GET /user-api/find/{id}` - Retrieve user details by ID
    - `PUT /user-api/update` - Update user details
    - `DELETE /user-api/delete/{id}` - Delete user by ID

- **Authentication:**

    - `POST /user-api/activate` - Activate a user account
    - `POST /user-api/login` - Authenticate a user
    - `POST /user-api/recoverPassword` - Recover a user's password

- **Account Management:**

    - `PATCH /user-api/changeStatus/{id}/{status}` - Change user status

## API Documentation

The API documentation is available at: [http://localhost:4041/swagger-ui.html](http://localhost:4041/swagger-ui.html)

## Configuration

The application configuration is defined in `application.yml` and includes settings for:

- **Database connection** (MySQL)
- **Email server** (SMTP for sending emails)
- **Application-specific properties** (messages for success/failure responses)

## Installation

1. **Clone the Repository:**

    ```bash
    git clone https://github.com/Krushit-Babariya/User-Management-API.git
    ```

2. **Navigate to the Project Directory:**

    ```bash
    cd User-Management-API
    ```

3. **Build the Project:**

    ```bash
    mvn clean install
    ```

4. **Run the Application:**

    ```bash
    mvn spring-boot:run
    ```
