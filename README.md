# Smart City Incident & Traffic Management Platform

This workspace contains a full-stack prototype for a Smart City platform built with React.js, Spring Boot 3, and MySQL.

## Project structure

- backend/ - Spring Boot REST API, JWT security, JPA entities, and business services
- frontend/ - React.js dashboard and role-based UI
- docs/ - architecture, database schema, API reference, and implementation plan

## Requirements checklist

- [x] React frontend with role-aware navigation
- [x] Spring Boot backend with layered architecture
- [x] JWT authentication and role-based access control
- [x] MySQL-ready schema with JPA entities and validation
- [x] Traffic, incident, emergency, civic issue, analytics, and notification flows
- [ ] Full production-grade hardening for external sensor integrations and live dispatching

## Architecture summary

The platform is structured around a layered backend where controllers delegate to services and repositories, while the frontend consumes REST APIs with Axios and displays dashboards, charts, and maps.

## Implementation phases

1. Project setup and database connection
2. Authentication and RBAC
3. Traffic and intersections
4. Incident and emergency resource workflows
5. Civic issue tracking
6. Dashboard, analytics, notifications, and map UI
7. Automated tests and validation
8. Documentation and final verification

## Local run instructions

### Backend

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm start
```

> The frontend is configured to talk to the backend at http://localhost:8080 by default.

## Demo accounts

Use the database seeder or create users through the registration endpoint.

## Database note

The application is configured for MySQL, while the test profile uses H2 so automated tests can run without a local MySQL instance.
