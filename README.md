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

- **Backend:** Java (Spring Boot)
- **Containerization:** Docker
- **Orchestration:** Kubernetes (manifests in `k8s/`)
- **CI/CD:** GitHub Actions workflows (`.github/workflows/`)
- **Security:** SonarCloud, CodeQL, Trivy

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

1. **Clone the repository:**
   ```bash
   git clone https://github.com/AnnieeMD/Modern-DevOps-Practices.git
   cd Modern-DevOps-Practices
   ```

2. **Build and run with Maven:**
   ```bash
   mvn clean package
   java -jar target/*.jar
   ```

3. **Access the API:**
   - Base URL: `http://localhost:8080/api/people`
   - Health check: `http://localhost:8080/actuator/health`

4. **Run with Docker:**
   ```bash
   docker build -t modern-devops-practices .
   docker run -p 8080:8080 modern-devops-practices
   ```

---

## Kubernetes Deployment

1. **Ensure you have a Kubernetes cluster running (e.g., kind, minikube, or cloud).**
2. **Apply manifests:**
   ```bash
   kubectl apply -f k8s/deployment.yaml
   kubectl apply -f k8s/service.yaml
   ```
3. **Port-forward to access the service:**
   ```bash
   kubectl port-forward svc/modern-devops 8080:80
   ```

---

