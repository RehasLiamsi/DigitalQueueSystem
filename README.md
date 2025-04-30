# Digital Queue System

The **Digital Queue System** is a real-time queue management application designed to streamline customer service operations.
Users can join queues via QR code scans or direct links, monitor their position in real-time, and exit queues when needed.
Administrators have full control over queue operations, including activation and closure, ensuring efficient service management.

## Features

- **User-Friendly Queue Entry**: Join queues effortlessly through QR codes or direct links.
- **Real-Time Updates**: Monitor your position in the queue with live updates.
- **Easy Exit**: Leave the queue at any time with a simple action.
- **Administrative Control**: Admins can activate or deactivate queues in real-time.
- **Responsive Design**: Optimized for various devices to ensure accessibility.

## Tech Stack

- **Backend**: Java with Spring Boot
- **Frontend**: Vue.js
- **Languages**: Java, JavaScript, HTML, CSS

## Getting Started

### Prerequisites

- Java 11 or higher
- Node.js and npm
- Vue CLI (for frontend development)

### Installation

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/RehasLiamsi/DigitalQueueSystem.git
   cd DigitalQueueSystem
   ```

2. **Backend Setup**:
   - Navigate to the backend directory:
     ```bash
     cd queue_backend/springboot_project
     ```
   - Build and run the Spring Boot application:
     ```bash
     ./mvnw spring-boot:run
     ```

3. **Frontend Setup**:
   - Navigate to the frontend directory:
     ```bash
     cd queue_frontend
     ```
   - Install dependencies:
     ```bash
     npm install
     ```
   - Run the development server:
     ```bash
     npm run serve
     ```

4. **Access the Application**:
   - Frontend: `http://localhost:8080/`
   - Backend API: `http://localhost:8081/`

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements or bug fixes.

## License

This project is licensed under the [MIT License](LICENSE).
