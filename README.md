# AI-FitAdvisor 💪🤖

<p align="center">
  <em>A scalable, microservices-based fitness platform offering AI-driven workout recommendations and comprehensive activity tracking.</em>
</p>

<p align="center">
  <a href="https://github.com/baidya-ripan-024/AI-FitAdvisor/stargazers"><img src="https://img.shields.io/github/stars/[your-github-username]/AI-FitAdvisor?style=for-the-badge&logo=github&color=FFC107" alt="Stars"></a>
  <a href="https://github.com/baidya-ripan-024/AI-FitAdvisor/network/members"><img src="https://img.shields.io/github/forks/[your-github-username]/AI-FitAdvisor?style=for-the-badge&logo=github&color=8BC34A" alt="Forks"></a>
  <a href="https://github.com/baidya-ripan-024/AI-FitAdvisor/issues"><img src="https://img.shields.io/github/issues/[your-github-username]/AI-FitAdvisor?style=for-the-badge&logo=github&color=FF5722" alt="Issues"></a>
  <a href="https://github.com/baidya-ripan-024/AI-FitAdvisor/blob/main/LICENSE"><img src="https://img.shields.io/github/license/[your-github-username]/AI-FitAdvisor?style=for-the-badge&color=03A9F4" alt="License"></a>
</p>

## ✨ About The Project

AI-FitAdvisor is not just another fitness tracker. It's a smart, personalized fitness companion designed to help users achieve their health goals effectively. By leveraging the power of Generative AI, this platform provides tailored workout plans and dietary suggestions that adapt to the user's progress and preferences in real-time.

Built on a robust and scalable **Microservice Architecture**, the system is designed for high availability, independent deployment of services, and seamless scalability.

**Live Demo:** [**website-link.com**](https://ai-fit-advisor.vercel.app/) 🚀


## 🎯 Key Features

* 🤖 **AI-Powered Recommendations**: Integrates with the **Gemini AI API** to generate dynamic, personalized workout and fitness plans based on user data and goals.
* 🔐 **Secure Authentication & Authorization**: Utilizes **Keycloak** for rock-solid security, providing role-based access control (RBAC) and protecting user data across all microservices.
* 📈 **Comprehensive Activity Tracking**: Allows users to log workouts, track progress, monitor metrics, and visualize their fitness journey over time.
* ⚡ **Asynchronous Communication**: Employs **RabbitMQ** as a message broker to ensure reliable, asynchronous communication between microservices, enhancing system resilience and responsiveness.
* 🐳 **Containerized Deployment**: Fully containerized with **Docker**, allowing for consistent development environments and simplified deployment.
* 🛠️ **Modular & Scalable**: Developed with a **Microservice Architecture**, enabling services to be developed, deployed, and scaled independently.


## 🏗️ Architecture & Tech Stack

This project is built using a modern, decoupled microservices architecture. The core services communicate both synchronously via REST APIs and asynchronously via a message queue.

### Architecture Diagram

```mermaid
graph TD
    subgraph User Interaction
        A[👨‍💻 User]
    end

    subgraph Infrastructure
        B(🌐 API Gateway)
        C(🔑 Keycloak Server)
        D(📬 RabbitMQ)
    end

    subgraph Microservices
        S1(UserService)
        S2(WorkoutService)
        S3(ActivityService)
        S4(RecommendationService)
    end

    subgraph Databases
        DB1[🐘 PostgreSQL]
        DB2[🍃 MongoDB]
    end

    subgraph External Services
        E[✨ Gemini AI API]
    end

    A --> B
    B -- Authenticates via --> C
    B -- Routes to --> S1
    B -- Routes to --> S2
    B -- Routes to --> S3

    S1 --- DB1
    S2 --- DB1
    S3 --- DB2

    S2 -- Publishes Event --> D
    S3 -- Publishes Event --> D

    S4 -- Subscribes to --> D
    S4 -- Calls --> E
    S4 -- Generates Plan & Publishes --> D
```

### 🛠️ Technologies Used

| Category           | Technology                                                                                                                                                                                            |
| ------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Backend**        | ![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) ![Spring](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring) |
| **Frontend**       | ![React](https://img.shields.io/badge/React+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) |
| **Databases**      | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-336791?style=for-the-badge&logo=postgresql) ![MongoDB](https://img.shields.io/badge/MongoDB-6.x-47A248?style=for-the-badge&logo=mongodb)     |
| **Security**       | ![Keycloak](https://img.shields.io/badge/Keycloak-22-00A98F?style=for-the-badge&logo=keycloak&logoColor=white)                                                                                        |
| **Messaging**      | ![RabbitMQ](https://img.shields.io/badge/RabbitMQ-3.9-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)                                                                                       |
| **DevOps & Tools** | ![Docker](https://img.shields.io/badge/Docker-20.10-2496ED?style=for-the-badge&logo=docker&logoColor=white) ![Maven](https://img.shields.io/badge/Maven-3.8-C71A36?style=for-the-badge&logo=apache-maven)  |
| **External API**   | ![Gemini](https://img.shields.io/badge/Gemini_AI-API-4A80F5?style=for-the-badge&logo=google-gemini&logoColor=white)                                                                                   |



## 🚀 Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

Make sure you have the following installed on your system:
* Java JDK 17+
* Docker & Docker Compose
* Maven or Gradle
* Git

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/baidya-ripan-024/AI-FitAdvisor.git
    cd AI-FitAdvisor
    ```

2.  **Set up Environment Variables:**
    You can Create your own .env file to set the Credentials. othrwise you can directly go to the config-server and for each server you can define the configuration. it totally upto you that how you confi 
 

3.  **Build and Run with Docker Compose:**
    This single command will build the images for all microservices and start the required containers (databases, message broker, etc.).
    ```sh
    docker-compose up --build -d
    ```

4.  **Access the Application:**
    * **API Gateway**: `http://localhost:8080`
    * **Keycloak Admin Console**: `http://localhost:8888`
    * **RabbitMQ Management**: `http://localhost:15672`



## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.


## 📬 Contact

[Ripan Baidya] - [baidya.ripan024@gmail.com](mailto:baidya.ripan024@gmail.com)

Project Link: [https://github.com/baidya-ripan-024/AI-FitAdvisor](https://github.com/baidya-ripan-024/AI-FitAdvisor)



<p align="center">
  <em>Thank you for checking out AI-FitAdvisor!</em>
</p>
