# Tutor Management System

A web-based platform designed for managing tutors, sessions, and students efficiently. The application features a full-stack setup using **Spring Boot** for the backend and **React** for the frontend.

## Features

- **Tutor Management**: Add, update, and delete tutor profiles.
- **Session Scheduling**: Manage tutoring sessions and allocate tutors.
- **Student Tracking**: Keep track of students and their assigned tutors.

## Tech Stack

- **Backend**: Java (Spring Boot)
- **Frontend**: React
- **Database**: MySQL
- **Build Tool**: Maven (for the backend)

## Prerequisites

Ensure you have the following installed:
- JDK 8+
- Node.js (latest stable version)
- Maven

## Setup Instructions

### Backend Setup (Spring Boot)

1. Clone the repository:
   ```bash
   git clone https://github.com/ALLENYL30/tutor-management-system.git
   cd tutor-management-system
   ```

2. Navigate to the backend directory:
   ```bash
   cd backend
   ```

3. Build the project using Maven:
   ```bash
   mvn install
   ```

4. Run the backend application:
   - Via your IDE, run the `main` class.
   - Or run from the command line:
     ```bash
     java -jar target/[Generated JAR File].jar
     ```

### Frontend Setup (React)

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install the dependencies:
   ```bash
   npm install
   ```

3. Start the React development server:
   ```bash
   npm start
   ```
   The application should now be running on `http://localhost:3000`.
