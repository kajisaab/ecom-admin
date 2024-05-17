# Ecommerce Admin/Vendor Portal

## Project Description
This project is an e-commerce platform specifically designed for vendor. It allows 
vendors to manage product and sales efficiently. The application is built using Spring Boot and leverages JWT for security, PostgreSQL for the database, and various other libraries for logging and testing.

## Project Structure
The project follows a modular structure to separate concerns and improve maintainability. Below is a brief overview of the main directories and their purpose:

````
ecommerce
├── .idea                   # IntelliJ IDEA project files
├── .mvn                    # Maven wrapper files
├── logs                    # Directory for log files
├── src
│   └── main
│       └── java
│           └── com.kajisaab.ecommerce
│               ├── common                    # Common utility classes
│               │   ├── DBEntity.java
│               │   └── LabelValuePair.java
│               ├── constants                 # Application constants
│               │   ├── UserRoleConstantEnum.java
│               │   └── UserTypeConstantEnum.java
│               ├── core                      # Core configurations and components
│               │   ├── configuration
│               │   │   ├── CustomAuthenticationEntryPoint.java
│               │   │   └── SecurityConfiguration.java
│               │   ├── exception
│               │   │   ├── ApiException.java
│               │   │   ├── BadRequestException.java
│               │   │   ├── GenerateErrorMessage.java
│               │   │   └── GlobalExceptionHandler.java
│               │   ├── interceptor
│               │   │   ├── AuthHandlerInterceptor.java
│               │   │   └── WebConfig.java
│               │   ├── jwt
│               │   │   ├── AuthService.java
│               │   │   ├── CustomUserDetailsService.java
│               │   │   ├── JwtAuthenticationFilter.java
│               │   │   └── JwtService.java
│               │   ├── OpenApiConfig
│               │   │   └── OpenApiConfig.java
│               │   ├── responseHandler
│               │   │   ├── ResponseDataHandler.java
│               │   │   ├── ResponseHandler.java
│               │   │   └── ResponseHandlerService.java
│               │   └── usecase
│               │       ├── Usecase.java
│               │       └── UsecaseRequest.java
│               ├── ping
│               │   └── PingController.java
│               ├── utils
│               │   ├── HelperUtils.java
│               │   └── JsonUtils.java
│               └── EcommerceApplication.java
│   └── resources
│       ├── static
│       ├── templates
│       ├── application.yml
│       ├── application-dev.yml
│       ├── banner.txt
│       └── logback.xml
│   └── test
│       └── java
│           └── com.kajisaab.ecommerce
│               ├── feature
│               │   ├── auth
│               │   │   └── repository
│               │   │       ├── UserCredentialRepositoryTests.java
│               │   │       └── UserRepositoryTests.java
│               │   ├── usecase
│               │   └── ping
│               │       └── EcommerceApplicationTests.java
│       └── resources
├── target                   # Compiled output files
├── .gitignore
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
````
## Getting Started

### Prerequisites
- Java 21
- Maven 3.6.3 or higher
- PostgresSQL

### Running the Application
1. Clone the repository
    ````
    git clone https://github.com/yourusername/ecommerce.git
    cd ecommerce
    ````
2. Set up the database.


- Update the application.yml and application-dev.yml with your PostgreSQL database credentials.
3. Build the application.
    ```
    mvn clean install
    ```
4. Run the application
    ```
    mvn spring-boot:run
    ```
5. Access the API endpoints at `http://localhost:3000`.

## Owner

- [Aman Khadka](https://github.com/kajisaab/)

## Contributing
1. Fort the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. push to the branch (`git push origin feature/your-feature`)
5. Create a new Pull Request

## License
This project is licensed under the [MIT License](https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt).

## Contact
For more information, please contact [Aman Khadka](amankhadkakaji@gmail.com)

- Happy coding! 🚀🚀🚀🚀🚀

