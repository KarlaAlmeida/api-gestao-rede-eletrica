# Management of Electrical Grid Maintenance

This project is a robust and scalable system for managing electrical grid maintenance. It provides a structured way to register and manage assets, technicians, incidents, and work orders, laying the groundwork for a comprehensive maintenance management platform.

## Objectives

The main goal is to structure a system for registering assets, technicians, incidents, and work orders to manage electrical grid maintenance.

### Specific Objectives

- **Asset, Technician, Incident, and Work Order Registration:** Create a clear and organized structure for registering these core entities.
- **Clear Associations:** Associate assets with incidents, and link them to technicians and work orders.
- **Efficient Information Retrieval:** Facilitate easy searching and retrieval of information.
- **Foundation for Future Features:** Establish a reliable base for future enhancements, including:
  - Asset history tracking.
  - Real-time integration with customer service or monitoring systems.
  - Automated preventive maintenance planning.
  - Reporting and performance indicators.
  - Role-based authentication and access control.

## Technologies and Tools

This project is built with a modern and robust technology stack:

- **Java 21:** The latest long-term support (LTS) version of Java.
- **Spring Boot 3:** For creating stand-alone, production-grade Spring-based applications.
- **Spring Web:** For building RESTful APIs.
- **Spring Data JPA:** To persist data in a SQL store with Java Persistence API using Spring Data and Hibernate.
- **H2 Database:** An in-memory database, ideal for development and testing.
- **Lombok:** Reduces boilerplate code for model and log objects.
- **Maven:** A powerful project management and comprehension tool.

## Project Structure

The project follows a classic layered architecture, promoting separation of concerns and maintainability:

- **`controller`:** Contains the REST controllers that expose the API endpoints.
- **`model/domain/entities`:** The JPA entities that represent the core domain objects.
- **`model/repository`:** The Spring Data JPA repositories for data access.
- **`model/service`:** The business logic layer of the application.
- **`model/loader`:** Classes responsible for loading initial data into the database at startup.

## How to Run

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   ```
2. **Navigate to the project directory:**
    ```bash
    cd karlaapi
    ```
3. **Run the application using Maven:**
    ```bash
    ./mvnw spring-boot:run
    ```
4. **Access the H2 Console:**
    - The application will start on port `8080`.
    - Open your browser and go to `http://localhost:8080/h2-console`.
    - Use the following settings to connect:
        - **Driver Class:** `org.h2.Driver`
        - **JDBC URL:** `jdbc:h2:~/databaseKarla`
        - **User Name:** `sa`
        - **Password:** (leave blank)

## API Endpoints

The application provides the following REST endpoints for managing the core entities:

- `POST /api/ativos`: Creates a new asset.
- `GET /api/ativos`: Retrieves all assets.
- `GET /api/ativos/{id}`: Retrieves a specific asset.
- `PUT /api/ativos/{id}`: Updates an asset.
- `DELETE /api/ativos/{id}`: Deletes an asset.

- `POST /api/tecnicos`: Creates a new technician.
- `GET /api/tecnicos`: Retrieves all technicians.
- `GET /api/tecnicos/{id}`: Retrieves a specific technician.
- `PUT /api/tecnicos/{id}`: Updates a technician.
- `DELETE /api/tecnicos/{id}`: Deletes a technician.

- `POST /api/ocorrencias`: Creates a new incident.
- `GET /api/ocorrencias`: Retrieves all incidents.
- `GET /api/ocorrencias/{id}`: Retrieves a specific incident.
- `PUT /api/ocorrencias/{id}`: Updates an incident.
- `DELETE /api/ocorrencias/{id}`: Deletes an incident.

- `POST /api/ordens-servico`: Creates a new work order.
- `GET /api/ordens-servico`: Retrieves all work orders.
- `GET /api/ordens-servico/{id}`: Retrieves a specific work order.
- `PUT /api/ordens-servico/{id}`: Updates a work order.
- `DELETE /api/ordens-servico/{id}`: Deletes a work order.

## Future Features

- Asset history recording.
- Real-time integration with customer calls or monitoring systems.
- Automated preventive maintenance planning.
- Reporting and performance indicators.
- Role-based authentication and access control.
