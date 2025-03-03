# userProfile
User Profile Microservice

# Technical Commands
- ### Backend (Spring Boot & Maven)
Build & Repackage Application:
Run in root of the project
```bash
mvn clean package spring-boot:repackage
```
This command cleans your project and builds it as an executable fat jar (includes all dependencies).

## Docker
- ### Build Docker Image:

```bash
docker build -t user-profile-service .
```
Creates a Docker image named user-profile-service from the Dockerfile in the project root.

- ### Run Docker Container:

```bash
docker run -p 8080:8080 user-profile-service
```
Starts the container, mapping port 8080 in the container to port 8080 on your local machine.

## Frontend (npm)
Go to 'frontend' directory from project root

- ### Install Dependencies:

```bash
cd frontend
npm install
```
Installs all required packages for the frontend.

- ### Start Frontend Server:
```bash
npm start
```
Launches the development server for the frontend.

# Design Choices and Assumptions

### Language and Framework
- **Java 17 & Spring Boot:**  
  I chose Java 17 for its modern language features and long-term support. Spring Boot accelerates development with its auto-configuration and extensive ecosystem, making it ideal for building microservices quickly and reliably.

### Architecture and Layers
- **Layered Architecture:**  
  The application follows a clean separation of concerns:
  - **Controller Layer:** Handles HTTP requests and responses.
  - **Service Layer:** Contains business logic (e.g., unique email verification).
  - **Repository Layer:** Manages data persistence using Spring Data JPA.
  
- **Assumptions:**
  - User profiles are a core resource containing fields like first name, last name, email, and phone number.
  - The microservice is stateless and can be easily scaled horizontally.
  - Input validation and error handling are critical for ensuring data integrity and a consistent API experience.

### Data Persistence
- **H2 Database for Development:**  
  I use an H2 in-memory database to facilitate rapid development and testing. In production, I assume a switch to a more robust solution like AWS RDS or DynamoDB, depending on scalability requirements.

### Error Handling and Validation
- **Global Exception Handling:**  
  A global exception handler ensures that any errors (e.g., a missing user profile) are captured and returned in a consistent format.
- **Input Validation:**  
  Built-in Spring validation mechanisms ensure that required fields are present and formatted correctly.

### Deployment Assumptions
- **AWS Free Tier as Starting Point:**  
  The service is initially deployed using AWS Free Tier resources, with containerization via Docker. This setup is designed to be flexible for scaling as needs evolve.

---

## How the Microservice Adheres to MACH Principles

### Microservices
- **Independent & Self-Contained:**  
  The User Profile Service is designed as a single, focused microservice that can be developed, deployed, and scaled independently of other services.
- **Loose Coupling:**  
  The service communicates via RESTful APIs, which helps in maintaining a decoupled architecture that is easier to modify and extend.

### API-first
- **RESTful API Design:**  
  The service exposes clear, well-documented endpoints for creating, reading, updating, and deleting user profiles.
- **Client Agnostic:**  
  By focusing on a clean API interface, the service can be consumed by any client (web, mobile, etc.) without being coupled to a specific front-end implementation.

### Cloud-native
- **Containerized Deployment:**  
  The service is containerized (using Docker) and designed to run on cloud platforms like AWS, ensuring scalability and high availability.
- **Built for Scalability:**  
  With stateless architecture and cloud-friendly configurations, the service is optimized for auto-scaling and load balancing in a cloud environment.
- **Cloud Integrations:**  
  Easy integration with AWS services (such as RDS for a production database, Elastic Beanstalk for deployment, and more) makes it a true cloud-native application.

### Headless
- **Separation of Concerns:**  
  The service focuses solely on managing user profile data and business logic, leaving presentation entirely up to the consuming client.
- **Decoupled Front-end:**  
  The API-first approach ensures that any front-end (e.g., a React or Angular application) can be built on top of this service without any dependency on its internal implementation.

---

## Final Thoughts

This User Profile Service was crafted with modern development practices in mind—balancing simplicity and scalability. By adhering to MACH principles, it remains flexible, cloud-ready, and fully decoupled from presentation logic, ensuring that it can evolve alongside your front-end or any other consuming service.
