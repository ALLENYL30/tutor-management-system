# Tutor Management Web Platform

## Overview

Tutor Management Web Platform is a full-stack application built using Java and React, designed for managing tutor information. The backend is built with Spring Boot, and the frontend is implemented using React.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

- JDK 8
- Node.js (latest stable version)
- Maven

## Installation Steps

### Setting Up the Backend (Java)

1. **Clone the Repository**:

   ```bash
   git clone https://github.com/unsw-cse-comp3900-9900-23T3/capstone-project-9900f15asuperstar.git
   cd capstone-project-9900f15asuperstar

   ```

2. **Navigate to the Backend Directory** (assuming it's at the project root):

   ```bash
   cd backend
   ```

3. **Build the Project with Maven**:

   ```bash
   mvn install
   ```

4. **Run the Application**:
   - Run the main class through your IDE.
   - Or, run via the command line:
     ```bash
     java -jar target/[Generated JAR File Name].jar
     ```

### Setting Up the Frontend (React)

1. **Navigate to the Frontend Directory** (assuming it's at the project root):

   ```bash
   cd frontend
   ```

2. **Install Dependencies**:

   ```bash
   npm install
   ```

3. **Start the Frontend Application**:
   ```bash
   npm start
   ```
   This will launch the application on `localhost:3000` (default port, unless changed).
