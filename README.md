### Note That this project is a WIP and is not yet complete.
### To be added: JUnit tests, Flyway migrations, Docker support, SonarQube integration, and more.

# Spring Boot REST API for Blog Posts and Comments
This is a portfolio REST API built with Spring Boot that allows users to perform CRUD (Create, Read, Update, Delete) operations on blog posts and comments. The API also provides JWT security to ensure that only authenticated users can access protected resources. Additionally, the API has swagger UI support.

## API Endpoints

### Authentication
| Endpoint  | HTTP Method | Description                                        |
|-----------|-------------|----------------------------------------------------|
| ```/login```    | POST        | Authenticate a user and return a JWT access token. |
| ```/register``` | POST        | Register a new user.                               | 


### Blog Posts
| Endpoint                     | HTTP Method | Description                                       |
|------------------------------|-------------|---------------------------------------------------|
| ```/posts   ```                    | POST        | Create a new blog post.                           |
| ```/posts    ```                   | GET         | Retrieve a paginated list of all blog posts.      |
| ```/posts/{id} ```                 | GET         | Retrieve a single blog post by ID.                |
| ```/posts/{id} ```                 | 	PUT        | 	Update an existing blog post by ID.              |
| ```/posts/{id}```	                 | DELETE	     | Delete an existing blog post by ID.               |
| ```/posts/category/{categoryId}``` | 	GET        | 	Retrieve all blog posts belonging to a category. |


### Blog Comments
| Endpoint                      | HTTP Method | Description                                      |
|-------------------------------|-------------|--------------------------------------------------|
| ```/posts/{postId}/comments```      | 	POST       | Create a new comment on a blog post.             |
| ```/posts/{postId}/comments```      | 	GET	       | Retrieve all comments on a blog post.            |
| ```/posts/{postId}/comments/{id}``` | 	GET	       | Retrieve a single comment on a blog post by ID.  |
| ```/posts/{postId}/comments/{id}``` | 	PUT	       | Update an existing comment on a blog post by ID. |
| ```/posts/{postId}/comments/{id}``` | DELETE      | Delete an existing comment on a blog post by ID. |

### Blog Categories
| Endpoint | HTTP Method | Description                                                       |
|----------|-------------|-------------------------------------------------------------------|
| ```/```	       | POST        | Add a new category to the system. Requires admin access.          |
| ```/{id}```    | GET         | Get the category with the specified ID.                           |
| ```/   ```     | GET         | Get a list of all categories in the system.                       |
| ```/{id}```    | PATCH       | Update the category with the specified ID. Requires admin access. |                               
| ```/{id} ```   | DELETE      | Delete the category with the specified ID. Requires admin access. |                              


## Security:
This API uses JWT (JSON Web Tokens) for authentication and authorization. Users must authenticate with the ```/login``` endpoint to receive a JWT access token, which is then required for accessing any protected resource.

## Dependencies:
- Spring Boot 3.0
- Spring Security
- Spring Data JPA
- MySQL
- Hibernate Validator
- Lombok
- SpringDoc OpenAPI

## Getting Started
To run the application locally, you will need to have MySQL installed and running. Then, follow these steps:

- Clone the repository to your local machine.
- Create a new MySQL database for the application.
- Edit the ```application.yaml``` file to configure the database connection and other properties as needed.
- Run the application using your favorite IDE or by running ./mvnw spring-boot:run in the terminal.
- Use a REST API client like Postman to interact with the API endpoints. You can also use the Swagger UI to test the API endpoints.

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License
This project is licensed under the MIT License - see the LICENSE file for details.