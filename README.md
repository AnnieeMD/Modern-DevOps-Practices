# Modern DevOps Practices

This project demonstrates a complete, automated software delivery process for a Java application, applying modern DevOps practices as part of the "Modern DevOps Practices" course. The solution covers key phases of the software development lifecycle, implements CI/CD pipelines, and leverages containerization, Kubernetes, security scanning, and more.

---

## Table of Contents

- [Project Overview](#project-overview)
- [Key DevOps Practices](#key-devops-practices)
- [Branch Strategy](#branch-strategy)
- [Architecture](#architecture)
- [CI/CD Pipeline](#cicd-pipeline)
- [Deep Dive: Static Application Security Testing (SAST)](#deep-dive-static-application-security-testing-sast)
- [How to Run Locally](#how-to-run-locally)
- [Kubernetes Deployment](#kubernetes-deployment)

---

## Project Overview

This repository contains a simple RESTful Java application for managing `Person` entities. The main goal is to showcase a robust, automated DevOps pipeline, including:

- Source control and branching strategies
- Automated build, test, and code quality checks
- Security scanning (SAST, container scanning)
- Dockerization and image management
- Continuous deployment to Kubernetes
- Infrastructure as code (Kubernetes manifests)
- Documentation as code

### API Endpoints

The application exposes the following REST API endpoints:

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/people` | Get all people |
| GET | `/api/people/{id}` | Get person by ID |
| POST | `/api/people` | Create a new person |
| PUT | `/api/people/{id}` | Update a person |
| DELETE | `/api/people/{id}` | Delete a person |
| GET | `/actuator/health` | Health check endpoint |

---

## Key DevOps Practices

This project demonstrates the following DevOps practices:

- **Collaboration & Source Control:** GitHub Issues, Pull Requests, and code reviews.
- **Branching & Workflow:** Feature branches, PR-based workflows, protected main branch.
- **CI/CD Automation:** GitHub Actions for building, testing, security scanning, and deployment.
- **Security:** Static Application Security Testing (SAST) with SonarCloud and CodeQL, container vulnerability scanning with Trivy.
- **Containerization & Orchestration:** Docker for packaging, Kubernetes for deployment.
- **Infrastructure as Code:** Kubernetes YAML manifests.
- **Documentation:** This README and code comments.

---

## Branch Strategy

- **Main branch:** Stable, production-ready code. Protected from direct pushes.
- **Feature branches:** Created from `main` for new features or fixes (`feature/xyz`).
- **Pull Requests:** All changes are merged via PRs, triggering CI checks and code review.
- **Issue-driven workflow:** Issues are opened for new features, bugs, or improvements, and linked to branches and PRs.

---

## Architecture

- **Backend:** Java (Spring Boot) with PostgreSQL database
- **Containerization:** Docker
- **Orchestration:** Kubernetes (manifests in `k8s/`)
- **CI/CD:** GitHub Actions workflows (`.github/workflows/`)
- **Security:** SonarCloud, CodeQL, Trivy

### Kubernetes Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                         Kubernetes Cluster                          │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌────────────────────────────────────────────────────────────┐     │
│  │                    ConfigMap & Secret                      │     │
│  ├────────────────────────────────────────────────────────────┤     │
│  │                                                            │     │
│  │  ┌──────────────────────┐    ┌──────────────────────┐      │     │
│  │  │ postgres-config      │    │ postgres-secret      │      │     │
│  │  ├──────────────────────┤    ├──────────────────────┤      │     │
│  │  │ POSTGRES_DB          │    │ POSTGRES_USER        │      │     │
│  │  └──────────────────────┘    │ POSTGRES_PASSWORD    │      │     │
│  │                              └──────────────────────┘      │     │
│  └────────────────────────────────────────────────────────────┘     │
│                    │                          │                     │
│                    │                          │                     │
│                    ▼                          ▼                     │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │              PostgreSQL Deployment (1 replica)              │    │
│  ├─────────────────────────────────────────────────────────────┤    │
│  │                                                             │    │
│  │  ┌────────────────────────────────────────────────────┐     │    │
│  │  │         Pod: postgres                              │     │    │
│  │  │  ┌──────────────────────────────────────────────┐  │     │    │
│  │  │  │  Container: postgres                         │  │     │    │
│  │  │  │  Image: postgres:16-alpine                   │  │     │    │
│  │  │  └──────────────────────────────────────────────┘  │     │    │
│  │  └────────────────────────────────────────────────────┘     │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                              │                                      │
│                              │ Exposed via                          │
│                              ▼                                      │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │         Service: postgres (ClusterIP)                       │    │ 
│  │         Port: 5432 → TargetPort: 5432                       │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                              ▲                                      │
│                              │ Connects to                          │
│                              │                                      │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │          Application Deployment (2 replicas)                │    │
│  ├─────────────────────────────────────────────────────────────┤    │
│  │                                                             │    │
│  │  ┌────────────────────────────────────────────────────┐     │    │
│  │  │         Pod: modern-devops-xxxxx                   │     │    │ 
│  │  │  ┌──────────────────────────────────────────────┐  │     │    │
│  │  │  │  Container: app                              │  │     │    │
│  │  │  │  Image: adermendzhieva/                      │  │     │    │
│  │  │  │         modern-devops-practices:latest       │  │     │    │
│  │  │  └──────────────────────────────────────────────┘  │     │    │
│  │  └────────────────────────────────────────────────────┘     │    │
│  │                                                             │    │
│  │  ┌────────────────────────────────────────────────────┐     │    │
│  │  │         Pod: modern-devops-yyyyy                   │     │    │
│  │  │  ┌──────────────────────────────────────────────┐  │     │    │
│  │  │  │  Container: app                              │  │     │    │
│  │  │  │  Image: adermendzhieva/                      │  │     │    │
│  │  │  │         modern-devops-practices:latest       │  │     │    │
│  │  │  └──────────────────────────────────────────────┘  │     │    │
│  │  └────────────────────────────────────────────────────┘     │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                              │                                      │
│                              │ Exposed via                          │
│                              ▼                                      │
│  ┌─────────────────────────────────────────────────────────────┐    │
│  │       Service: modern-devops (ClusterIP)                    │    │
│  │       Port: 80 → TargetPort: 8080                           │    │
│  │       Load balances between 2 app pods                      │    │
│  └─────────────────────────────────────────────────────────────┘    │
│                              │                                      │
└──────────────────────────────┼──────────────────────────────────────┘
                               │
                               │ Access via
                               ▼
                    kubectl port-forward svc/modern-devops 8080:80
                               │
                               ▼
                      http://localhost:8080
```

### Key Components

**Configuration Management:**
- `postgres-config` ConfigMap: Stores database name
- `postgres-secret` Secret: Stores database username and password

**Database Layer:**
- PostgreSQL Deployment: Single replica running postgres:16-alpine
- PostgreSQL Service: ClusterIP service exposing port 5432

**Application Layer:**
- Application Deployment: 2 replicas for high availability
- Application Service: ClusterIP service load-balancing traffic to app pods

---

## CI/CD Pipeline

The pipeline is fully automated and starts from a GitHub repository:

1. **Open Issue / Create Feature Branch:** Collaboration via GitHub Issues and branching.
2. **Pull Request:** Triggers CI pipeline.
3. **Unit Test & Build:** Maven runs tests and builds the application.
4. **Static Code Analysis (SAST):** SonarCloud and CodeQL scan for code quality and security issues.
5. **Docker Build:** Application is containerized.
6. **Container Vulnerability Scan:** Trivy scans the Docker image for vulnerabilities.
7. **Push to Registry:** Docker image is pushed to Docker Hub.
8. **Deploy to Kubernetes:** Rolling deployment using manifests.
9. **Smoke Test:** Health endpoint is checked post-deployment.

**Workflows:**
- `.github/workflows/ci.yaml`: CI pipeline (build, test, SAST)
- `.github/workflows/codeql.yaml`: CodeQL SAST
- `.github/workflows/cd.yaml`: CD pipeline (build image, scan, deploy to Kubernetes)

---

## Deep Dive: Static Application Security Testing (SAST)

### Tools Used

- **SonarCloud:** Integrates with Maven to analyze code for bugs, vulnerabilities, and code smells.
- **CodeQL:** GitHub-native static analysis for detecting security issues in Java code.

### How SAST is Integrated

- **SonarCloud:** Runs in the CI pipeline (`ci.yaml`) after tests. It checks for:
  - Code quality issues
  - Security hotspots
  - Vulnerabilities (e.g., SQL injection, XSS)
- **CodeQL:** Runs in a dedicated workflow (`codeql.yaml`) on every push/PR. It:
  - Builds a code database
  - Runs security queries to find vulnerabilities
  - Reports results in the GitHub Security tab

### Why SAST Matters

SAST helps catch security issues early in the software development lifecycle, reducing risk and cost. Automated SAST ensures every code change is checked before merging or deploying.

---

## How to Run Locally

### Prerequisites

- Docker and Docker Compose installed

### Steps

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AnnieeMD/Modern-DevOps-Practices.git
   cd Modern-DevOps-Practices
   ```

2. **Create a `docker-compose.yaml` file in the project root:**

   ```yaml
   version: '3.8'
   services:
     postgres:
       image: postgres:16-alpine
       environment:
         POSTGRES_DB: example_db
         POSTGRES_USER: example_user
         POSTGRES_PASSWORD: example_password
       ports:
         - "5432:5432"
       healthcheck:
         test: ["CMD-SHELL", "pg_isready -U example_user -d example_db"]
         interval: 5s
         timeout: 5s
         retries: 5

     app:
       build: .
       ports:
         - "8080:8080"
       environment:
         SPRING_DATASOURCE_URL: jdbc:postgresql://postgres:5432/example_db
         SPRING_DATASOURCE_USERNAME: example_user
         SPRING_DATASOURCE_PASSWORD: example_password
         SPRING_JPA_HIBERNATE_DDL_AUTO: update
       depends_on:
         postgres:
           condition: service_healthy
   ```

   > **Note:** Replace `example_db`, `example_user`, and `example_password`.

3. **Start the application:**
   ```bash
   docker-compose up --build
   ```

4. **Access the API:**
   - Base URL: `http://localhost:8080/api/people`
   - Health check: `http://localhost:8080/actuator/health`
---

## Kubernetes Deployment

### Example Files in Repository

The files `k8s/postgres-secret.yaml` and `k8s/postgres-config.yaml` contain **example placeholder values only**:
- They serve as templates showing the Kubernetes resource structure
- They demonstrate Infrastructure as Code practices
- They are **NOT used** in the automated CI/CD pipeline

Production credentials are stored securely as **GitHub Secrets**.

The CD pipeline (`.github/workflows/cd.yaml`) creates Kubernetes ConfigMap and Secret **dynamically** from GitHub Secrets.

### Local Kubernetes Deployment

For local testing, use the example files directly.
> **Note:** Replace `example_db`, `example_user`, and `example_password`.

#### Quick Start

```bash
# Create cluster
kind create cluster --name modern-devops

# Build and load image
mvn clean package -DskipTests
docker build -t adermendzhieva/modern-devops-practices:latest .
kind load docker-image adermendzhieva/modern-devops-practices:latest --name modern-devops

# Deploy everything using example files
kubectl apply -f k8s/postgres-config.yaml -f k8s/postgres-secret.yaml -f k8s/postgres-deployment.yaml -f k8s/postgres-service.yaml
kubectl wait --for=condition=ready pod -l app=postgres --timeout=60s
kubectl apply -f k8s/deployment.yaml -f k8s/service.yaml

# Access the application
kubectl port-forward svc/modern-devops 8080:80 &
curl http://localhost:8080/api/people
```
---

