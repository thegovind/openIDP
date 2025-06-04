<center><h2 align="center">Infosys DevOps Platform</h2></center>
</div>
<br/>

<div align="center">
For more information, check out below links:
<h6><a href="https://www.youtube.com/watch?time_continue=1&v=t8cuE3Jog2U" target="_blank">IDP for Agile and DevOps at Scale for Digital transformation</a></h6>
<h6><a href="https://www.youtube.com/watch?v=8V0OKlB-d2I" target="_blank">IDP for accelerated DevOps journey</a></h6>
</div>
<hr/>

# About the repository

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/3d3d1f4d519445e88b6c16f1c3ddc5c2)](https://www.codacy.com/app/idp-oss/openIDP?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Infosys/openIDP&amp;utm_campaign=Badge_Grade)

This repository is intended to hold the code base along with the required dependencies for the Infosys DevOps Platform (IDP) OSS version.

## Getting Started

Below instructions will help the users to get to know the structure of this repository. See [WIKI](https://github.com/Infosys/openIDP/wiki) for notes on how to deploy the platform on a live system.

## Details on project modules

<table>
<colgroup>
<col width="100" />
<col width="200" />
<col width="100" />
</colgroup>

<tbody>
<tr>
  <th align="center">Module/File</th>
  <th align="center">Description</th>
  <th align="center">Tech-stack details</th>
</tr>
<tr>
  <td align="center"><sup><b>Dashboard</b></sup></td>
  <td><sup>This microservice is designed for collecting relevant data during pipeline execution in Jenkins with the help of IDP's custom utilities. The data is then stored in Postgres and fed to Grafana dashboard, which is being used as the visualisation tool for providing trends and insights about applications. </sup>

</td>
  <td>
    <ol>
    <li><sup><b>Programming Language :</b> Java, for custom utilities</sup></li>
    <li><sup><b>Dependency Management:</b> Maven</sup></li>
    <li><sup><b>Database :</b> Postgres</sup></li>
    <li><sup><b>Metrics Dashboard :</b> Grafana</sup></li>
    </ol>
  </td>
</tr>
<tr>
  <td align="center"><sup><b>DSL</b></sup></td>
  <td><sup>This module allows the developer to describe Jenkins jobs using Groovy-based language. The module uses <a href="https://wiki.jenkins.io/display/JENKINS/Job+DSL+Plugin">Jenkins Job DSL Plugin</a> which creates, updates and controls the Jenkins jobs. This allows to embrace the concept of "Configuration as a Code", thereby reducing MTTR of CICD Server (Jenkins)</sup></td>
  <td>
    <ol>
    <li><sup><b>Programming Language :</b> Groovy</sup></li>
    <li><sup><b>Dependency Management:</b> Gradle</sup></li>
    </ol>
  </td>
</tr>
<tr>
  <td align="center"><sup><b>UI</b></sup></td>
  <td><sup>This module holds all the IDP user interface code and its related dependencies</sup></td>
  <td>
    <ol>
    <li><sup><b>Framework :</b> Angular 5</sup></li>
    <li><sup><b>Dependency Management:</b> NPM</sup></li>
    </ol>
  </td>
</tr>
<tr>
  <td align="center"><sup><b>Services</b></sup></td>
  <td><sup>This module is divided into number of sub-parts.</sup>
     <ol>
    <li><sup>Spring RESTful service module for creating stateless services for implementing business logic. These services can be independently deployed and scaled up using a load balancer</sup></li>
    <li><sup>OAuth2 authorization module for enhanced security</sup></li>
    <li><sup>Module implementing Zuul Proxy to be used as an edge gateway which would route the request through Authorization Server (if Access token is not available/expired)</sup></li>
     <li><sup>Database layer for performing CRUD operations on backend database with respect to the operation being performed</sup></li>
     <li><sup>Swagger for automatic documentation of RESTful Services</sup></li>
    </ol>
    
 </td>
  <td>
    <ol>
    <li><sup><b>Framework :</b> Spring Boot, Spring MVC, OAUth2.0</sup></li>
    <li><sup><b>Dependency Management:</b> Maven</sup></li>
    <li><sup><b>Database:</b> Postgres</sup></li>
    </ol>
  </td>
</tr>
<tr>
  <td align="center"><sup><b>Scheduler</b></sup></td>
  <td><sup>This Java based component is for timed execution of pipelines.</sup></td>
  <td>  <ol>
    <li><sup><b>Framework :</b> Spring Boot</sup></li>
    <li><sup><b>Dependency Management:</b> Maven</sup></li>
    </ol></td>
</tr>
<tr>
  <td align="center"><sup><b>Jenkins</b></sup></td>
  <td><sup>This folder contains the necessary configuration files and plugins for Jenkins to make it work smoothly with IDP. It also holds the custom tools being used by IDP for performing various operations. These custom tools are placed in Jenkins custom tools directory, from where they can be copied to any of the build agents for running some specific errands, for instance, pushing the analysis results to dashboard while the build is still running.</sup></td>
  <td align="center"><sup>-</sup></td>
</tr>
<tr>
  <td align="center"><sup><b>docs</b></sup></td>
  <td><sup>This folder contains the assets used in the Wiki pages.</td>
  <td align="center"><sup>-</sup></td>
</tr>
<tr>
  <td align="center"><sup><b>Data Files</b></sup></td>
  <td><sup>This folder contains the database initialization scripts for Postgres. It also consists of the Grafana configuration file (grafana.ini) with all the necessary configurations for integrating Grafana dashboard with IDP</sup></td>
  <td align="center"><sup>-</sup></td>
</tr>
</tbody>
</table>


## Deployment

The platform supports deployment using both Docker Swarm and Kubernetes for container orchestration.

### Kubernetes Deployment (Recommended)

#### Prerequisites
- Kubernetes cluster (v1.19+)
- Helm 3.x
- kubectl configured to access your cluster
- Persistent storage provisioner

#### Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/thegovind/openIDP.git
   cd openIDP
   ```

2. **Deploy using Kubernetes**
   ```bash
   # Using build script
   ./build.sh --kubernetes --namespace openidp

   # Or using Helm directly
   helm install openidp k8s/openidp --namespace openidp --create-namespace
   ```

3. **Access the platform**
   - Web UI: http://openidp.local/idpapp/
   - Jenkins: http://openidp.local/jenkins/
   - Grafana: http://openidp.local/grafana/

#### Kubernetes Deployment Options

```bash
# Development deployment
./build.sh --kubernetes --namespace openidp-dev

# SSL-enabled deployment
./build.sh --kubernetes --ssl-enabled --hostname your-domain.com

# Production deployment with custom values
helm install openidp k8s/openidp -f k8s/openidp/values-prod.yaml --namespace openidp
```

### Docker Swarm Deployment (Legacy)

#### Prerequisites
- Docker Engine 19.03+
- Docker Compose 1.25+
- Docker Swarm mode enabled

#### Quick Start

1. **Build and deploy**
   ```bash
   ./build.sh
   ```

2. **Access the platform**
   - Web UI: http://localhost/idpapp/
   - Jenkins: http://localhost/jenkins/
   - Grafana: http://localhost/grafana/

### Health Monitoring

Use the included health check script to verify all services:

```bash
# For Docker Swarm
./health_check.sh

# For Kubernetes
./health_check.sh --kubernetes --namespace openidp
```

### Migration from Docker Swarm to Kubernetes

To migrate an existing Docker Swarm deployment to Kubernetes:

1. **Export data** from existing deployment
2. **Deploy Kubernetes** version with same configuration
3. **Import data** to new deployment
4. **Update DNS/load balancer** to point to Kubernetes ingress
5. **Verify functionality** and decommission old deployment

See [k8s/README.md](k8s/README.md) for detailed Kubernetes deployment documentation.

Please read [WIKI](https://github.com/Infosys/openIDP/wiki) for detailed documentation.


