# userProfile
User Profile Microservice

## Technical Commands
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
