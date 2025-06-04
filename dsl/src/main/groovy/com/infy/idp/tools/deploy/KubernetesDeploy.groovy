/***********************************************************************************************
 *
 * Copyright 2018 Infosys Ltd.
 * Use of this source code is governed by MIT license that can be found in the LICENSE file or at
 * https://opensource.org/licenses/MIT.
 *
 ***********************************************************************************************/

package com.infy.idp.tools.deploy

import com.infy.idp.utils.ConfigurationManager

/**
 *
 * This class has the method to create Kubernetes deployment steps for deploy jobs
 *
 */

class KubernetesDeploy {
    
    private def script
    private def configManager
    
    KubernetesDeploy(script) {
        this.script = script
        this.configManager = new ConfigurationManager(script)
    }
    
    static def addStepsKubernetesLinux(context, jsonData, envIndex, stepIndex, envVar) {
        def envObj = jsonData.deployInfo.deployEnv[envIndex]
        def stepObj = envObj.deploySteps[stepIndex]
        
        def namespace = stepObj.kubernetesDeployment.namespace ?: "default"
        def helmChart = stepObj.kubernetesDeployment.helmChart ?: "k8s/openidp"
        def releaseName = stepObj.kubernetesDeployment.releaseName ?: "openidp"
        def valuesFile = stepObj.kubernetesDeployment.valuesFile ?: "values.yaml"
        
        context.steps {
            shell("""
                echo "Starting Kubernetes deployment for Linux"
                echo "Deploying to Kubernetes namespace: ${namespace}"
                
                # Create namespace if it doesn't exist
                kubectl create namespace ${namespace} --dry-run=client -o yaml | kubectl apply -f -
                
                # Add Helm repositories
                helm repo add bitnami https://charts.bitnami.com/bitnami
                helm repo update
                
                # Deploy using Helm
                helm upgrade --install ${releaseName} ${helmChart} \\
                    --namespace ${namespace} \\
                    --values ${helmChart}/${valuesFile} \\
                    --wait --timeout=600s
                
                # Verify deployment
                kubectl get pods -n ${namespace}
                kubectl get services -n ${namespace}
                
                echo "Kubernetes deployment completed successfully"
            """)
        }
    }
    
    static def addStepsKubernetesWindows(context, jsonData, envIndex, stepIndex, envVar) {
        def envObj = jsonData.deployInfo.deployEnv[envIndex]
        def stepObj = envObj.deploySteps[stepIndex]
        
        def namespace = stepObj.kubernetesDeployment.namespace ?: "default"
        def helmChart = stepObj.kubernetesDeployment.helmChart ?: "k8s/openidp"
        def releaseName = stepObj.kubernetesDeployment.releaseName ?: "openidp"
        def valuesFile = stepObj.kubernetesDeployment.valuesFile ?: "values.yaml"
        
        context.steps {
            batchFile("""
                echo "Starting Kubernetes deployment for Windows"
                echo "Deploying to Kubernetes namespace: ${namespace}"
                
                kubectl create namespace ${namespace} --dry-run=client -o yaml | kubectl apply -f -
                
                helm repo add bitnami https://charts.bitnami.com/bitnami
                helm repo update
                
                helm upgrade --install ${releaseName} ${helmChart} ^
                    --namespace ${namespace} ^
                    --values ${helmChart}/${valuesFile} ^
                    --wait --timeout=600s
                
                kubectl get pods -n ${namespace}
                kubectl get services -n ${namespace}
                
                echo "Kubernetes deployment completed successfully"
            """)
        }
    }
    
    static def rollbackKubernetes(context, namespace, releaseName) {
        context.steps {
            shell("""
                echo "Rolling back Kubernetes deployment"
                helm rollback ${releaseName} --namespace ${namespace}
                kubectl get pods -n ${namespace}
                echo "Kubernetes rollback completed"
            """)
        }
    }
    
    static def uninstallKubernetes(context, namespace, releaseName) {
        context.steps {
            shell("""
                echo "Uninstalling Kubernetes deployment"
                helm uninstall ${releaseName} --namespace ${namespace}
                kubectl delete namespace ${namespace} --ignore-not-found=true
                echo "Kubernetes uninstall completed"
            """)
        }
    }
}
