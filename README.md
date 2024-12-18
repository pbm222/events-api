# Sport Events API

This is a RESTful API for managing sport events. It allows users to create, retrieve, and update sport events with filtering options for status and sport type. The API is secured, and only users with the `ADMIN` role can access certain endpoints.

## Features

- **Create a sport event**
- **Retrieve sport events with optional filters (status, sport)**
- **Retrieve a sport event by ID**
- **Update the status of a sport event**

## Endpoints

### 1. Get Sport Events with Optional Filters
**GET /sport-events**  
Fetch all sport events or filter by status and sport type.

**Parameters**
- `status` (optional): Filter by event status (e.g., `ACTIVE`, `INACTIVE`, `FINISHED`).
- `sport` (optional): Filter by sport type.

**Example**
```bash
GET /sport-events?status=ACTIVE&sport=Football
```

### 2. Get Sport Event by ID
**GET /sport-events/{id}**  
Fetch a sport event by its ID.

**Parameters**
- `id`: The ID of the sport event.

**Example**
```bash
GET /sport-events/1
```

### 3. Create a Sport Event
**POST  /sport-events**  
Create a new sport event. The name field is mandatory.

**Request Body**
```bash
{
  "name": "Test Event",
  "sport": "Football",
  "status": "ACTIVE",
  "startTime": "2024-12-18T10:00:00"
  }
  ```

### 4. Update Sport Event Status
**PATCH /sport-events/{id}/status**  
Update the status of a sport event.

**Parameters**
- `id`: The ID of the sport event to update.
- `newStatus`: The new status of the event (e.g., ACTIVE, INACTIVE, FINISHED).

**Request body**
```bash
{
  "newStatus": "FINISHED"
}
```

## Swagger UI
Swagger provides a user-friendly interface to interact with the API. It can be accessed at the following URL:

Swagger UI: http://localhost:8080/swagger-ui.html

## Security
The API uses role-based access control (RBAC) with the ADMIN role required for certain actions.
The @PreAuthorize annotation ensures that only users with the ADMIN role can modify sport events.
