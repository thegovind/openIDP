# openIDP Kubernetes Deployment

This directory contains Kubernetes manifests and Helm charts for deploying the openIDP platform on Kubernetes.

## Overview

The openIDP platform has been migrated from Docker Swarm to Kubernetes orchestration, providing better scalability, reliability, and management capabilities. The deployment uses Helm charts to manage the complex microservices architecture.

## Architecture

The openIDP platform consists of 17 microservices:

### Infrastructure Services
- **PostgreSQL**: Primary database for all services
- **Zookeeper**: Coordination service for Kafka
- **Kafka**: Message broker for inter-service communication
- **Keycloak**: Identity and access management

### Application Services
- **Config Server**: Centralized configuration management
- **Eureka**: Service discovery
- **OAuth**: Authentication and authorization
- **Services**: Core business logic APIs
- **Dashboard**: Metrics and monitoring dashboard
- **UI**: Angular frontend application
- **Subscription**: Subscription management
- **Scheduler**: Job scheduling service
- **Jenkins Connector**: Jenkins integration service
- **Orchestrator**: Pipeline orchestration
- **Jenkins**: CI/CD automation server
- **Grafana**: Monitoring and visualization
- **Proxy**: Nginx reverse proxy

## Prerequisites

- Kubernetes cluster (v1.19+)
- Helm 3.x
- kubectl configured to access your cluster
- Persistent storage provisioner (for StatefulSets)

## Quick Start

### 1. Install Dependencies

```bash
# Add Bitnami Helm repository
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```

### 2. Deploy openIDP

```bash
# Create namespace
kubectl create namespace openidp

# Deploy with default values
helm install openidp ./openidp --namespace openidp

# Or deploy with custom values
helm install openidp ./openidp --namespace openidp -f values-dev.yaml
```

### 3. Verify Deployment

```bash
# Check pod status
kubectl get pods -n openidp

# Check services
kubectl get services -n openidp

# Check ingress
kubectl get ingress -n openidp
```

## Configuration

### Values Files

The chart includes several pre-configured values files:

- `values.yaml`: Default configuration
- `values-dev.yaml`: Development environment settings
- `values-prod.yaml`: Production environment settings
- `values-ssl.yaml`: SSL-enabled configuration

### Key Configuration Options

```yaml
# Basic configuration
config:
  hostname: openidp.local
  customPort: 80
  ssl:
    enabled: false

# Resource limits
resources:
  limits:
    cpu: 500m
    memory: 512Mi
  requests:
    cpu: 250m
    memory: 256Mi

# Persistence
persistence:
  enabled: true
  storageClass: ""
  size: 8Gi

# High availability
replicaCount: 2
autoscaling:
  enabled: true
  minReplicas: 2
  maxReplicas: 10
```

## Deployment Modes

### Development Mode

```bash
helm install openidp ./openidp -f values-dev.yaml --namespace openidp
```

Features:
- Single replica for all services
- Smaller resource requests
- Local storage
- HTTP only

### Production Mode

```bash
helm install openidp ./openidp -f values-prod.yaml --namespace openidp
```

Features:
- Multiple replicas for high availability
- Higher resource limits
- Persistent storage with fast SSDs
- SSL/TLS enabled
- Auto-scaling enabled
- Pod anti-affinity rules

### SSL Mode

```bash
helm install openidp ./openidp -f values-ssl.yaml --namespace openidp
```

Features:
- HTTPS enabled
- SSL certificates mounted
- Secure communication between services

## Health Checks and Monitoring

All services include comprehensive health checks:

### Liveness Probes
- Check if the service is running
- Restart pod if probe fails
- Configured with appropriate delays and timeouts

### Readiness Probes
- Check if the service is ready to accept traffic
- Remove pod from service endpoints if probe fails
- Faster response times than liveness probes

### Monitoring Endpoints

Each service exposes health check endpoints:

- Config Server: `/config/idpoauth/paas`
- Eureka: `/eureka`
- Keycloak: `/auth`
- OAuth: `/idp-oauth/login`
- Services: `/idprest/swagger-ui.html`
- Dashboard: `/idpdashboard/1`
- UI: `/idpapp/`
- Subscription: `/subscription/swagger-ui.html`
- Scheduler: `/idpschedule/actuator/health`
- Grafana: `/grafana`
- Jenkins: `/jenkins`
- Jenkins Connector: `/idpjenkins/actuator/health`

## Persistent Storage

The following services require persistent storage:

- **PostgreSQL**: Database data (`/var/lib/postgresql/data`)
- **Jenkins**: Build data and configurations (`/var/jenkins_home`)
- **Grafana**: Dashboards and settings (`/var/lib/grafana`)
- **Zookeeper**: Coordination data (`/var/lib/zookeeper`)
- **Kafka**: Message logs (`/var/lib/kafka`)
- **DSL**: Shared DSL scripts (`/DSL`)

## Networking

### Service Discovery
Services communicate using Kubernetes DNS:
- `config:8888`
- `eureka:8761`
- `postgresql:5432`
- etc.

### Ingress
The Nginx proxy service handles external traffic routing:
- All external requests go through the proxy
- Proxy routes to appropriate backend services
- Supports both HTTP and HTTPS

### Load Balancing
Kubernetes provides automatic load balancing:
- Round-robin distribution
- Health check integration
- Automatic failover

## Scaling

### Manual Scaling

```bash
# Scale a specific service
kubectl scale deployment openidp-services --replicas=3 -n openidp

# Scale using Helm
helm upgrade openidp ./openidp --set services.replicaCount=3 -n openidp
```

### Auto Scaling

```yaml
autoscaling:
  enabled: true
  minReplicas: 2
  maxReplicas: 10
  targetCPUUtilizationPercentage: 70
```

## Troubleshooting

### Check Pod Status

```bash
kubectl get pods -n openidp
kubectl describe pod <pod-name> -n openidp
kubectl logs <pod-name> -n openidp
```

### Check Service Connectivity

```bash
kubectl get services -n openidp
kubectl port-forward service/openidp-proxy 8080:80 -n openidp
```

### Check Persistent Volumes

```bash
kubectl get pv
kubectl get pvc -n openidp
```

### Common Issues

1. **Pods stuck in Pending**: Check storage class and node resources
2. **Services not accessible**: Verify ingress configuration and DNS
3. **Database connection issues**: Check PostgreSQL pod status and credentials
4. **SSL certificate issues**: Verify certificate secrets and mounting

## Migration from Docker Swarm

The Kubernetes deployment maintains full compatibility with the Docker Swarm version:

- All environment variables preserved
- Same service communication patterns
- Identical health check endpoints
- Compatible volume mounting strategies

### Migration Steps

1. Export data from Docker Swarm deployment
2. Deploy Kubernetes version with same configuration
3. Import data to new deployment
4. Update DNS/load balancer to point to Kubernetes ingress
5. Verify all services are working
6. Decommission Docker Swarm deployment

## Security

### RBAC
The chart creates appropriate service accounts and RBAC rules for each service.

### Secrets Management
Sensitive data is stored in Kubernetes secrets:
- Database passwords
- API keys
- SSL certificates

### Network Policies
Consider implementing network policies to restrict inter-pod communication.

## Backup and Recovery

### Database Backup

```bash
kubectl exec -it openidp-postgresql-0 -n openidp -- pg_dump -U postgres keycloak > backup.sql
```

### Persistent Volume Backup

Use your storage provider's snapshot functionality or tools like Velero for comprehensive backup.

## Support

For issues and questions:
1. Check the troubleshooting section
2. Review pod logs and events
3. Consult the original openIDP documentation
4. Open an issue in the project repository
