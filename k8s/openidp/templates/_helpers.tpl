{{/*
Expand the name of the chart.
*/}}
{{- define "openidp.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "openidp.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "openidp.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "openidp.labels" -}}
helm.sh/chart: {{ include "openidp.chart" . }}
{{ include "openidp.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "openidp.selectorLabels" -}}
app.kubernetes.io/name: {{ include "openidp.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "openidp.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "openidp.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}

{{/*
Common environment variables
*/}}
{{- define "openidp.commonEnv" -}}
- name: TZ
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: TZ
- name: PROFILE
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: PROFILE
- name: SSL_ENABLED
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: SSL_ENABLED
{{- end }}

{{/*
Database environment variables
*/}}
{{- define "openidp.dbEnv" -}}
- name: POSTGRES_HOSTNAME
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: POSTGRES_HOSTNAME
- name: POSTGRES_PORT
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: POSTGRES_PORT
- name: POSTGRES_DEFAULT
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: POSTGRES_DEFAULT
- name: POSTGRES_USER
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: POSTGRES_USER
- name: POSTGRES_PASSWORD
  valueFrom:
    secretKeyRef:
      name: {{ include "openidp.fullname" . }}-secret
      key: POSTGRES_PASSWORD
{{- end }}

{{/*
Kafka environment variables
*/}}
{{- define "openidp.kafkaEnv" -}}
- name: KAFKA_HOSTNAME
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: KAFKA_HOSTNAME
- name: KAFKA_PORT
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: KAFKA_PORT
{{- end }}

{{/*
Service discovery environment variables
*/}}
{{- define "openidp.serviceEnv" -}}
- name: CONFIG_HOSTNAME
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: CONFIG_HOSTNAME
- name: CONFIG_PORT
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: CONFIG_PORT
- name: CONFIG_USERNAME
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: CONFIG_USERNAME
- name: CONFIG_PASSWORD
  valueFrom:
    secretKeyRef:
      name: {{ include "openidp.fullname" . }}-secret
      key: CONFIG_PASSWORD
- name: EUREKA_HOSTNAME
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: EUREKA_HOSTNAME
- name: EUREKA_PORT
  valueFrom:
    configMapKeyRef:
      name: {{ include "openidp.fullname" . }}-config
      key: EUREKA_PORT
{{- end }}
