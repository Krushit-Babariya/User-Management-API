# User Management API

## Overview

The User Management API is a RESTful service designed to handle user information and authentication. This API allows for the management of user accounts, including operations such as creating, retrieving, updating, and deleting user profiles. It also provides secure authentication mechanisms for user login and registration.

## Features

- **User Management:** Create, read, update, and delete user profiles.
- **Authentication:** Secure login and registration with JWT-based authentication.
- **Error Handling:** Comprehensive error messages and validation for a better user experience.
- **API Documentation:** Interactive API documentation using Swagger.

## Technologies Used

- **Backend Framework:** Spring Boot
- **Programming Language:** Java
- **Database:** [Specify database if used, e.g., MySQL, H2]
- **Authentication:** JWT (JSON Web Tokens)
- **API Documentation:** Swagger
- **Build Tool:** Maven

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

## API Endpoints

- **User Management:**

    - `POST /api/users` - Create a new user
    - `GET /api/users/{id}` - Retrieve user details by ID
    - `PUT /api/users/{id}` - Update user details by ID
    - `DELETE /api/users/{id}` - Delete user by ID

- **Authentication:**

    - `POST /api/auth/register` - Register a new user
    - `POST /api/auth/login` - Authenticate a user and obtain a JWT

## API Documentation

The API documentation is available at:

