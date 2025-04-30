# Digital Queue System

The **Digital Queue System** is a full-stack, real-time queue management application that simplifies queue operations for both users and administrators. It provides a seamless interface for users to join queues via QR codes or direct links, while administrators can manage queues efficiently through a dedicated admin panel.

---

## 🧰 Tech Stack

### Backend
- Java 11+
- Spring Boot
- Spring Security with JWT Authentication
- RESTful API
- Maven

### Frontend
- Vue.js (separate interfaces for User and Admin)
- HTML/CSS/JavaScript

---

## 📁 Project Structure

```
DigitalQueueSystem/
├── queue_backend/
│   └── springboot_project/       # Spring Boot backend
├── queue_frontend/
│   ├── admin_frontend/           # Admin Vue frontend
│   └── user_frontend/            # User Vue frontend
├── README.md
└── LICENSE
```

---

## 🚀 Features

### User Frontend
- Join queue via QR code or link
- View real-time queue position
- Leave queue with one click

### Admin Frontend
- View real-time queue list
- Manage queue flow (open/close queues)
- Access user statistics

### Backend
- Secure API with JWT-based authentication
- Modular architecture: controllers, services, configs
- Configurable security settings

---

## 🛠️ Getting Started

### Prerequisites
- Java 11+
- Node.js and npm
- Vue CLI
- Docker (optional for deployment)

### Installation

#### 1. Clone the Repository
```bash
git clone https://github.com/RehasLiamsi/DigitalQueueSystem.git
cd DigitalQueueSystem
```

#### 2. Start Backend
```bash
cd queue_backend/springboot_project
./mvnw spring-boot:run
```

#### 3. Start Frontend (User/Admin)
```bash
# For User Frontend
cd ../../queue_frontend/user_frontend
npm install
npm run serve

# For Admin Frontend
cd ../../queue_frontend/admin_frontend
npm install
npm run serve
```

#### 4. Docker (optional)
```bash
cd queue_backend/springboot_project
docker compose up
```

---

## 📌 API Highlights

- `POST /auth/register` – Register new user/admin
- `POST /auth/authenticate` – Authenticate and receive JWT
- `GET /queue/all` – Retrieve current queue
- `POST /queue/join` – Join a queue
- `DELETE /queue/leave/{id}` – Leave the queue
- `POST /queue/activate` – Admin action to activate queue

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Contributing

Contributions are welcome! Please fork the repo and submit a pull request.

