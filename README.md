# Tax Estimator Service

This project is a Java-based microservice for estimating taxes. It uses Spring Boot, MongoDB, and is built with Maven.

---

## Branch Protection and CI Requirements

To ensure high code quality and stable deployments, the repository enforces the following:

- **Protected Main Branch**  
  Direct pushes to the `main` branch are disabled. All changes must be submitted via Pull Requests (PRs).

- **Continuous Integration (CI) Build**  
  Every PR triggers a GitHub Actions workflow that runs a Maven build (`./mvnw clean install`).  
  PRs cannot be merged unless this build passes successfully.

- **Automatic Branch Deletion**  
  After a PR is merged, the source branch is automatically deleted to keep the repository tidy.

---

## Getting Started

### Prerequisites

- Java 8 (Corretto 8 recommended)
- Maven (the project includes Maven Wrapper for convenience)
- Docker (for building container images)
- AWS CLI (if deploying to AWS EKS or ECR)

### Running Locally

1. Clone the repository.
2. Configure your local `application-local.yml` or `application-dev.yml` with appropriate environment variables or secrets.
3. Build the project with the Maven Wrapper:

   ```bash
   ./mvnw clean install
4. java -jar target/app.jar