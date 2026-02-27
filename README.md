# 📰 Free News Magazine API

A backend REST API built with **Java Spring Boot** for managing news articles, categories, and users.  
This project demonstrates clean architecture principles, CRUD operations, and backend best practices.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Docker & Docker Compose
- Git

---

## 📌 Features

- Create, update, delete and view news articles
- Category management
- User management
- Structured layered architecture (Controller, Service, Repository)
- RESTful API design
- Database integration with PostgreSQL
- Dockerized application setup

---

## 🏗 Architecture

The project follows a clean layered architecture:

Controller → Service → Repository → Database

This structure ensures:
- Maintainable code
- Separation of concerns
- Scalability

---

## 🔐 (If you implemented authentication)

- JWT-based authentication
- Role-based authorization
- Secure endpoints

(If not implemented, remove this section.)

---

## 🛠 How to Run the Project

### Using Docker

```bash
docker-compose up --build
