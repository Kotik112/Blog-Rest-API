# Spring Boot REST API for Blog Posts and Comments
This is a portfolio REST API built with Spring Boot that allows users to perform CRUD (Create, Read, Update, Delete) operations on blog posts and comments. The API also provides JWT security to ensure that only authenticated users can access protected resources. Additionally, the API has swagger UI support.

## API Endpoints

### Authentication
| Endpoint | HTTP Method | Description |
| /login | POST | Authenticate a user and return a JWT access token. |
| /register | POST | Register a new user. | 