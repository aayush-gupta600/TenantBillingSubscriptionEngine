# Tenant Billing & Subscription Engine

A multi-tenant SaaS billing backend built with **Spring Boot**, designed to manage subscription plans, tenant subscriptions, metered usage, automated invoicing, payments, and role-based access control.

## Features

* Multi-tenant SaaS architecture
* Tenant and user management
* Subscription plan management
* Subscription upgrades and downgrades
* Metered usage tracking
* Usage-based billing and overage calculation
* Invoice generation
* Invoice line-item management
* Stripe payment integration
* JWT-based authentication
* Role-based authorization with Spring Security
* BCrypt password hashing
* Stateless JWT authentication
* PostgreSQL persistence

## Tech Stack

* **Java 21**
* **Spring Boot**
* **Spring Security**
* **Spring Data JPA**
* **JWT**
* **PostgreSQL**
* **Stripe**
* **Lombok**
* **Maven**

## Project Structure

```text
src/main/java/com/college/tenantbilling
│
├── Model
│   ├── User.java
│   ├── Tenant.java
│   ├── Plan.java
│   ├── Subscription.java
│   ├── UsageRecord.java
│   ├── Invoice.java
│   ├── InvoiceLineItem.java
│   ├── Payment.java
│   ├── RoleType.java
│   └── TenantStatus.java
│
├── Repo
│   ├── UserRepo.java
│   ├── TenantRepo.java
│   ├── PlanRepo.java
│   ├── SubscriptionRepo.java
│   └── ...
│
├── Security
│   ├── UserDetailsImpl.java
│   ├── UserDetailsServiceImpl.java
│   ├── JwtUtils.java
│   ├── AuthTokenFilter.java
│   └── AuthEntryPointJwt.java
│
└── Config
    └── SecurityConfig.java
```

## System Architecture

```text
                         Tenant Billing System

                              ┌─────────┐
                              │  User   │
                              └────┬────┘
                                   │
                              belongs to
                                   │
                              ┌────▼────┐
                              │ Tenant  │
                              └────┬────┘
                                   │
                              subscribes to
                                   │
                              ┌────▼────┐
                              │  Plan   │
                              └────┬────┘
                                   │
                              Subscription
                                   │
                         ┌─────────▼─────────┐
                         │   UsageRecord     │
                         └─────────┬─────────┘
                                   │
                              Billing
                                   │
                              ┌────▼────┐
                              │ Invoice │
                              └────┬────┘
                                   │
                         ┌─────────▼─────────┐
                         │ InvoiceLineItem   │
                         └─────────┬─────────┘
                                   │
                              ┌────▼────┐
                              │ Payment │
                              └────┬────┘
                                   │
                              ┌────▼────┐
                              │ Stripe  │
                              └─────────┘
```

## Authentication & Security

The application uses **JWT-based stateless authentication** with Spring Security.

### Login Flow

```text
User Login
    ↓
AuthenticationManager
    ↓
DaoAuthenticationProvider
    ↓
UserDetailsService
    ↓
UserRepository
    ↓
BCrypt Password Verification
    ↓
Authenticated User
    ↓
JWT Generated
```

### Request Flow

After login, the client sends the JWT with each protected request:

```text
Client
  ↓
Authorization: Bearer <JWT>
  ↓
AuthTokenFilter
  ↓
Validate JWT
  ↓
Extract User Information
  ↓
Load UserDetails
  ↓
SecurityContext
  ↓
Role Authorization
  ↓
Controller
```

## Role-Based Access Control

The application supports role-based authorization.

```text
ADMIN
 ├── Manage tenants
 ├── Manage subscription plans
 ├── Manage billing configuration
 └── Administrative operations

TENANT_USER
 ├── View subscription
 ├── View usage
 ├── View invoices
 └── Make payments
```

## Multi-Tenant Data Isolation

Each user belongs to a tenant, and tenant-specific resources are associated with that tenant.

```text
Tenant A
 ├── Users
 ├── Subscription
 ├── Usage Records
 ├── Invoices
 └── Payments

Tenant B
 ├── Users
 ├── Subscription
 ├── Usage Records
 ├── Invoices
 └── Payments
```

The application is designed so that users can only access resources belonging to their own tenant.

## Billing Flow

```text
Tenant selects a Plan
        ↓
Subscription created
        ↓
Usage recorded
        ↓
Billing period processed
        ↓
Usage calculated
        ↓
Invoice generated
        ↓
InvoiceLineItems created
        ↓
Payment initiated
        ↓
Stripe processes payment
        ↓
Payment status updated
        ↓
Invoice marked as paid
```

## Core Entities

### Tenant

Represents a customer or organization using the SaaS platform.

### User

Represents a user belonging to a tenant and contains authentication and role information.

### Plan

Defines subscription pricing, billing interval, and included usage limits.

### Subscription

Connects a tenant with its currently selected plan.

### UsageRecord

Stores metered usage such as API calls, storage usage, or seats.

### Invoice

Represents a bill generated for a subscription and billing period.

### InvoiceLineItem

Represents an individual charge on an invoice, such as a base subscription fee or usage overage.

### Payment

Stores payment information and Stripe payment intent details associated with an invoice.

## Stripe Integration

Payments are designed around Stripe PaymentIntents.

```text
Invoice
   ↓
Create PaymentIntent
   ↓
Stripe
   ↓
Payment Processing
   ↓
Stripe Webhook
   ↓
Update Payment
   ↓
Update Invoice
```

The `Payment` entity stores the Stripe PaymentIntent ID so payment status can be tracked and verified.

## Database

The application uses **PostgreSQL** with Spring Data JPA for persistence.

Example database configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tenant_billing
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## JWT Configuration

Configure the JWT secret and expiration time:

```properties
spring.app.jwtSecret=your-secure-secret
spring.app.jwtExpirationMs=86400000
```

Do not commit real secrets, passwords, API keys, or Stripe credentials to the repository.

## Getting Started

### Prerequisites

* Java 21
* Maven
* PostgreSQL
* Stripe account for payment integration

### Clone

```bash
git clone <repository-url>
cd TenantBilling
```

### Configure the Database

Create a PostgreSQL database and update:

```text
src/main/resources/application.properties
```

with your database credentials.

### Run the Application

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bash
mvnw.cmd spring-boot:run
```

## API Authentication

Public authentication endpoints:

```text
/api/auth/**
```

Protected endpoints require:

```text
Authorization: Bearer <JWT>
```

## Development Status

### Completed

* Entity/model layer
* JPA entity relationships
* Repository layer
* JWT utilities
* JWT authentication filter
* Authentication entry point
* Spring Security configuration
* BCrypt password encoding
* Role-based security foundation

### In Progress

* User registration
* User login
* Authentication APIs
* Subscription services
* Usage billing logic
* Invoice generation
* Stripe PaymentIntent integration
* Stripe webhook handling
* Tenant-level authorization
* API testing

## Future Improvements

* Refresh token support
* Automated recurring billing
* Advanced tenant isolation
* Billing history
* Pagination and filtering
* OpenAPI/Swagger documentation
* Comprehensive unit and integration testing
* Docker support
* Production deployment

## License

This project is currently developed as a personal backend engineering project and learning implementation.
