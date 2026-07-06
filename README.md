# 🏥 MediSecure – Smart Hospital Management System

MediSecure is a full-stack Hospital Management System developed using **Java Spring Boot**, **Thymeleaf**, **Spring Security**, and **MySQL**. It simplifies hospital operations by managing patients, doctors, appointments, billing, reports, notifications, and administrative tasks through a secure web application.

---

## 📌 Features

### 👨‍⚕️ Patient Module
- Patient Registration
- Patient Login
- Book Online Appointment
- View Appointment Status
- Appointment Token Generation
- Email Notifications
- Medical History Management

### 🩺 Doctor Module
- Doctor Login
- View Daily Appointments
- Patient Queue Management
- Consultation Records
- Profile Management

### 🧑‍💼 Receptionist Module
- Patient Registration
- Appointment Scheduling
- Token Generation
- Patient Profile Management

### 👨‍💻 Admin Module
- Dashboard
- Manage Doctors
- Manage Receptionists
- Manage Patients
- Analytics Dashboard
- Activity Logs
- Reports Management
- System Settings

### 💳 Billing Module
- Generate Bills
- Payment Records
- Billing History

### 📊 Reports
- Excel Report Export
- PDF Report Export
- Analytics Dashboard

### 🔔 Notification Module
- Email Notifications
- Appointment Confirmation
- OTP Verification
- Token Notification

### 📱 SMS Integration
- Twilio SMS Support

---

# 🛠 Tech Stack

## Backend
- Java 17
- Spring Boot 3.5.3
- Spring MVC
- Spring Data JPA
- Spring Security
- Hibernate

## Frontend
- Thymeleaf
- HTML5
- CSS3
- JavaScript

## Database
- MySQL

## Build Tool
- Maven

## Additional Libraries
- Lombok
- Apache POI (Excel Export)
- iText 7 (PDF Export)
- Twilio SDK
- Spring Mail

---

# 📂 Project Structure

```
MediSecure
│
├── src/main/java/com/medsecure
│   ├── controller
│   ├── service
│   ├── repository
│   ├── entity
│   ├── config
│   ├── security
│   └── export
│
├── src/main/resources
│   ├── templates
│   ├── static
│   ├── application.properties
│   └── database
│
├── pom.xml
└── README.md
```

---

# ⚙️ Prerequisites

Install the following before running the project:

- Java JDK 17
- Maven
- MySQL Server
- Spring Tool Suite (STS) or IntelliJ IDEA
- Git (Optional)

---

# 🗄 Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE medisecure;
```

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medisecure
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# 📧 Email Configuration

Configure Gmail SMTP:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=yourgmail@gmail.com
spring.mail.password=your-app-password

spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

---

# 📱 Twilio Configuration

Configure Twilio credentials inside the application:

- Account SID
- Auth Token
- Twilio Phone Number

These are used for sending SMS notifications.

---

# ▶️ Running the Project

Clone the repository:

```bash
git clone https://github.com/yourusername/MediSecure.git
```

Navigate into the project:

```bash
cd MediSecure
```

Build the project:

```bash
mvn clean install
```

Run the application:

```bash
mvn spring-boot:run
```

Or run the `MediSecureApplication.java` class directly from your IDE.

The application starts at:

```
http://localhost:8080
```

---

# 📁 Main Modules

- Authentication
- Admin Dashboard
- Doctor Dashboard
- Reception Dashboard
- Patient Dashboard
- Appointment Management
- Billing Management
- Reports
- Analytics
- Notifications
- Activity Logs

---

# 📦 Maven Dependencies

- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Data JPA
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Mail
- MySQL Connector
- Lombok
- Apache POI
- iText7
- Twilio SDK

---

# 🔒 Security Features

- Spring Security Authentication
- Role-Based Access Control
- Secure Login
- Session Management

---

# 📊 Future Enhancements

- Video Consultation
- AI Disease Prediction
- Online Payment Gateway
- Mobile Application
- QR Code Based Registration
- Cloud Deployment
- Docker Support

---

# 👨‍💻 Developed By

**Devi Prasanna**

B.Tech Computer Science Engineering

Java Full Stack Developer

---

# 📜 License

This project is developed for educational and academic purposes.
