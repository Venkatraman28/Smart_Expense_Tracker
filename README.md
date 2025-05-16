# 💸 Smart Expense Tracker

A full-featured **Smart Expense Tracker** backend built using **Spring Boot**, **Java**, **MySQL**, **Keycloak**, **Python**, and **RabbitMQ**. The application supports secure user authentication, real-time expense tracking, budgeting, and intelligent AI-driven predictions.

## 🚀 Tech Stack

- **Backend**: Java, Spring Boot
- **Security**: Keycloak (OAuth2, JWT)
- **Database**: MySQL
- **AI & ML**: Python
- **Messaging**: RabbitMQ
- **Authentication**: Role-based access with Keycloak
- **API Documentation**: Swagger (OpenAPI 3)
- **Frontend (WIP)**: Angular 19 (Standalone Components + Keycloak integration)

## 📁 Project Structure

- `User` — Stores user details and authentication mapping.
- `Category` — Stores expense categories.
- `Transaction` — Stores user transactions (expenses/income).
- `Budget` — Stores monthly/weekly budget plans.
- `RabbitMQ Integration` — Connects Spring Boot with Python for AI predictions.
- `Python Services` — Provides AI/ML predictions via RabbitMQ.

## 🧠 AI-Powered Features

- ✅ **Auto-categorize transactions** based on descriptions.
- 📊 **Smart prediction of monthly expenses** based on user history.
- 💰 **Smart budget recommendations** based on trends and previous months.
- 🧠 AI logic handled using **Python** services and **RabbitMQ** messaging.

## 🔐 Authentication

- Keycloak is used for secure user login and role management.
- Access tokens (JWT) are used to secure REST endpoints.

## 🛠️ Features

- [x] User registration & login (Keycloak)
- [x] CRUD for Category, Transaction, Budget
- [x] AI-powered category suggestion
- [x] Monthly expense prediction
- [x] Budget prediction based on spending patterns
- [x] Role-based API access
- [x] RESTful APIs with JSON response
- [x] Integrated Swagger UI for API exploration

## 📜 Swagger UI

You can explore all available APIs with request/response examples via Swagger UI: localhost:8080/swagger-ui/index.html#/
