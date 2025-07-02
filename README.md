# 💇‍♀️ Salon Management System Backend

A microservices-based backend system for managing salon operations, appointments, payments, reviews, and more.

## 📖 Overview
This project is a **comprehensive salon management system** developed using a **Spring Boot microservices architecture**. It includes features such as user authentication, booking management, service categorization, payments, reviews, and real-time notifications.

---
## 🚀 Features

- 🔐 User Authentication & Authorization (via Keycloak)
- 🧑‍💼 Salon & Service Management
- 📅 Appointment Booking & Scheduling
- 💳 Payment Processing (via Stripe)
- 📝 Review & Rating System
- 🔔 Real-time Notifications
- 🧭 API Gateway with Service Discovery

---

## 🏗️ Microservices Architecture
The system consists of the following microservices:

| Microservice             | Responsibility                                 |
|--------------------------|------------------------------------------------|
| **User Service**         | User registration, authentication, roles       |
| **Salon Service**        | Salon registration, details, and updates       |
| **Booking Service**      | Handles appointments and scheduling logic      |
| **Services Offering**    | Lists available salon services                 |
| **Category Service**     | Manages service categories                     |
| **Payment Service**      | Processes and tracks transactions              |
| **Review Service**       | Handles user reviews and ratings               |
| **Notification Service** | Sends real-time alerts and updates             |
| **Gateway Server**       | API gateway to route external/internal requests|
| **Eureka Server**        | Service discovery and registration             |

---




## 🧰 Tech Stack

### ⚙️ Backend

- `Spring Boot` – Microservices framework  
- `Spring Cloud` – Microservice coordination  
- `Eureka` – Service discovery  
- `Spring Cloud Gateway` – API Gateway  
- `Keycloak` – Identity and access management  
- `JWT` – Token-based security  
- `MySQL` – Relational database  
- `RabbitMQ` – Message broker  
- `WebSocket` – Real-time communication

### 🎨 Frontend

- `React.js` – UI library  
- `Redux` – State management  
- `TailwindCSS` – Utility-first CSS  
- `Material UI` – Component library  
- `Formik` – Form handling

### 🧪 DevOps

- `Docker` – Containerization and orchestration

### 💳 Payment Integration

- `Stripe` – Secure payment gateway


---

## 🔧 Prerequisites

Make sure you have the following installed on your machine:

- Java 17+
- Node.js
- MySQL
- Docker
- IntelliJ IDEA (for backend)
- VS Code (for frontend)

---


## ⚙️ Setup & Installation

### Backend Setup

1. **🔁 Clone the repository**
   ```bash
   git clone https://github.com/your-username/salon-management-system-backend.git
   ```

2. **🔑 Keycloak Setup**
    ```bash
   docker run -p 8080:8080 -e KC_BOOTSTRAP_ADMIN_USERNAME=admin -e KC_BOOTSTRAP_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:26.1.0 start-dev
    ```
    ****Keycloak Configuration****
    - Create a new client
    - Configure client settings (Client ID, Secret)
    - Create roles (`CUSTOMER`, `SALON_OWNER`)
    - Create admin user
    - Increase access token lifespan

5. **🛸 Start RabbitMQ**
   ```bash
   docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management
    ```

6. **🧱 Start Backend Microservices**
   Start each microservice from its respective folder in the following order:
    1. **Eureka Server**
    2. **Gateway Server**
    3. **All other services** (`User`, `Booking`, `Salon`, etc.)
  
    Each microservice must be configured with the correct `application.yml` or `application.properties` file including:
  
    - `server.port`
    - `spring.application.name`
    - `eureka.client.service-url.defaultZone`
    - Database connection (`spring.datasource.*`) for DB-based services



## 🧭 Service Discovery

The project uses Netflix Eureka for service discovery.

- All microservices register themselves with the Eureka Server.
- Eureka Server provides dynamic resolution for internal service communication.

## 🚪 API Gateway

All client-side and frontend requests go through the Spring Cloud Gateway.

Key Features:
- ✅ Centralized Routing
- ♻️ Load Balancing
- 🔁 Path Rewriting
- 🔐 Security Filtering (integrated with Keycloak)

