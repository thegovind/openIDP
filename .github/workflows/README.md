# GitHub Actions CI/CD Pipeline

This repository uses GitHub Actions for continuous integration and deployment, replacing the previous Travis CI setup.

## Workflows

### CI Pipeline (`ci.yml`)
- Builds all Java microservices using Maven
- Builds Angular UI application
- Builds Jenkins custom tools
- Creates and pushes Docker images to GitHub Container Registry
- Runs on push/PR to main branches

### DSL Build (`dsl.yml`)
- Builds the Groovy DSL component using Gradle
- Triggered only when DSL files change
- Uploads DSL artifacts

### Security Scanning (`security.yml`)
- CodeQL analysis for Java and JavaScript
- Dependency vulnerability scanning with Trivy
- Container image security scanning
- Runs on push/PR and weekly schedule

### Release Management (`release.yml`)
- Creates release artifacts
- Publishes versioned Docker images
- Triggered on GitHub releases or manual dispatch

### Deployment (`deploy.yml`)
- Deploys to staging/production environments
- Uses existing build.sh script for deployment
- Manual trigger with environment selection

## Environment Variables

Required secrets for deployment:
- `DEPLOYMENT_HOSTNAME`: Target deployment hostname
- `POSTGRES_PASSWORD`: PostgreSQL password
- `KEYCLOAK_PASSWORD`: Keycloak admin password
- `JENKINS_USERNAME`: Jenkins admin username
- `JENKINS_PASSWORD`: Jenkins admin password

## Migration from Travis CI

The GitHub Actions workflows replace the previous `.travis.yml` configuration:
- Java service builds now use matrix strategy for parallel execution
- Docker builds use multi-stage builds with layer caching
- Security scanning is integrated into the CI pipeline
- Artifact management is handled through GitHub Packages

## Services Built

The CI pipeline builds the following components:
- **Java Services**: Services, OAuth, Cloud Config, Eureka, Dashboard, Subscription, Scheduler, Jenkins Connector, Orchestrator
- **UI**: Angular frontend application
- **Jenkins Tools**: Custom tools for DevOps automation
- **DSL**: Groovy-based domain-specific language for Jenkins job definitions

## Docker Images

All Docker images are published to GitHub Container Registry under:
- `ghcr.io/thegovind/openidp/[service-name]:[tag]`

Available tags:
- Branch names (e.g., `master`, `main`)
- Pull request numbers (e.g., `pr-123`)
- Git SHA (e.g., `sha-abc123`)
- Release versions (e.g., `v1.7.0`)
