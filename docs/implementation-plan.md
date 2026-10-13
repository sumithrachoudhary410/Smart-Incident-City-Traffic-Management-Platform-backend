# Smart City Platform Implementation Plan

## 1. Workspace inspection

The workspace was empty at the start of implementation. A root project folder was created, and the backend/frontend directories were scaffolded to match the requested structure.

## 2. Requirements checklist

- React.js frontend using JavaScript and role-based routes
- Java 21 / Spring Boot 3 backend with Maven
- MySQL schema with JPA/Hibernate mapping and validation
- JWT-based authentication and BCrypt password hashing
- Traffic management, incidents, emergency resources, civic issues, notifications, dashboard analytics
- REST APIs with source data from a database-backed service layer
- Automated tests and documentation

## 3. System architecture

### Backend layers

- Controller: REST endpoints for auth, traffic, incidents, resources, issues, analytics, notifications
- Service: business logic, validation, JWT, analytics, shortest-route calculations
- Repository: Spring Data repositories for persistence and query operations
- MySQL/Hibernate: normalized relational data model
- Security: JWT filter, BCrypt password encoder, method security

### Frontend layers

- Router and role-aware navigation
- API service layer using Axios
- Generic dashboard cards, tables, forms, and map placeholders
- Charting for congestion, incident trends, and issue status

## 4. Database schema

Core tables:

- users
- intersections
- roads
- traffic_observations
- incidents
- emergency_resources
- incident_assignments
- civic_issues
- departments
- notifications

Relationships are modeled as JPA associations with foreign keys and timestamps. The database is normalized to keep the structure consistent with the use cases.

## 5. Project folder structure

```text
smart-city-platform/
├── backend/
│   ├── src/main/java/com/smartcity/platform
│   ├── src/main/resources
│   └── src/test/java/com/smartcity/platform
├── frontend/
│   ├── src/
│   └── package.json
├── docs/
├── README.md
└── .gitignore
```

## 6. Milestones and status

### Phase 1 - Project setup and MySQL connection
- Create Maven and React project scaffolds
- Configure application properties for MySQL and profiles
- Ensure backend and frontend can run locally

### Phase 2 - Authentication and RBAC
- Register/login flows with JWT authentication
- User roles and BCrypt password hashing
- Protected routes and authorization checks

### Phase 3 - Intersection and traffic management
- Intersections, roads, linear traffic observations
- Congestion classification and density scoring
- Historical trend data and recommended timing logic

### Phase 4 - Incident and emergency resource management
- Incident lifecycle workflow
- Emergency resource availability and route calculation
- Assignment logic and historical tracking

### Phase 5 - Civic issue management
- Issue submission, assignment, search, filters, and status transitions
- Validation and file-attachment checks

### Phase 6 - Dashboard, maps, analytics, and notifications
- KPI cards and charts from database records
- Map integration points and event notifications

### Phase 7 - Automated tests and bug fixes
- Unit tests for services and validation logic
- Integration validation of the main API flows

### Phase 8 - Documentation and final verification
- README, architecture, API reference, and troubleshooting guide
- Build/test validation and honest issue tracking

## 7. Delivery notes

This project creates a working academic prototype that can be extended with production-grade integrations later. It intentionally keeps simulation logic and demo data explicit so the system remains realistic without pretending to operate live city infrastructure.
