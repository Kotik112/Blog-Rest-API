# Spring Boot REST API for Blog Posts and Comments
This is a REST API built with Spring Boot that allows users to perform CRUD (Create, Read, Update, Delete) operations on blog posts and comments. The API also provides JWT security to ensure that only authenticated users can access protected resources. Additionally, the API has swagger UI support.

## API Endpoints
- All endpoints have a prefix of ```/api/v1/```. The base URL is omitted in the endpoints below.
- Make sure to register and login to recieve a functional JWT. Use the JWT to authorize yourself to the endpoints that require admin role.

### Authentication
| Endpoint        | HTTP Method | Description                                        |
|-----------------|-------------|----------------------------------------------------|
| ```/auth/login```    | POST        | Authenticate a user and return a JWT access token. |
| ```/auth/register``` | POST        | Register a new user.                               | 


### Blog Posts

| Endpoint                           | HTTP Method | Description                                      | Requires admin role |
|------------------------------------|-------------|--------------------------------------------------|---------------------|
| ```/posts```                       | POST        | Create a new blog post.                          | :heavy_check_mark:  |
| ```/posts```                       | GET         | Retrieve a paginated list of all blog posts.     | :x:                 |
| ```/posts/{id}```                  | GET         | Retrieve a single blog post by ID.               | :x:                 |
| ```/posts/{id}```                  | PUT         | 	Update an existing blog post by ID.             | :heavy_check_mark:  |
| ```/posts/{id}```                  | DELETE	     | Delete an existing blog post by ID.              | :heavy_check_mark:  |
| ```/posts/category/{categoryId}``` | GET         | Retrieve all blog posts belonging to a category. | :heavy_check_mark:  |

### Blog Comments
| Endpoint                            | HTTP Method | Description                                      | Requires admin role |
|-------------------------------------|-------------|--------------------------------------------------|---------------------|
| ```/posts/{postId}/comments```      | 	POST       | Create a new comment on a blog post.             | :heavy_check_mark:  |
| ```/posts/{postId}/comments```      | 	GET	       | Retrieve all comments on a blog post.            | :x:                 |
| ```/posts/{postId}/comments/{id}``` | 	GET	       | Retrieve a single comment on a blog post by ID.  | :x:                 |
| ```/posts/{postId}/comments/{id}``` | 	PUT	       | Update an existing comment on a blog post by ID. | :heavy_check_mark:  |
| ```/posts/{postId}/comments/{id}``` | DELETE      | Delete an existing comment on a blog post by ID. | :heavy_check_mark:  |

### Blog Categories
| Endpoint                | HTTP Method | Description                                 | Requires admin role |
|-------------------------|-------------|---------------------------------------------|---------------------|
| ```/categories/```	     | POST        | Add a new category to the system.           | :heavy_check_mark:  |
| ```/categories/{id}```  | GET         | Get the category with the specified ID.     | :x:                 |
| ```/categories/   ```   | GET         | Get a list of all categories in the system. | :x:                 |
| ```/categories/{id}```  | PATCH       | Update the category with the specified ID.  | :heavy_check_mark:  |                       
| ```/categories/{id} ``` | DELETE      | Delete the category with the specified ID.  | :heavy_check_mark:  |                          

Check out the Swagger UI for more details on the API endpoints. (http://localhost:8080/swagger-ui/index.html)

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
- Run the application using your favorite IDE or by running ```./mvnw spring-boot:run``` in the terminal.
- Use a REST API client like Postman to interact with the API endpoints. You can also use the Swagger UI to test the API endpoints.

## License
This project is licensed under the MIT License - see the ```MIT_LICENSE file``` for details.
