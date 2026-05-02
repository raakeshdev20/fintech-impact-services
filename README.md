### 🚀 Fintech Impact Services (Dev Repository)
### 📌 Overview

This repository contains the Fintech Spring Boot microservice that exposes an API for impact-based test selection.

It is part of a cross-repository CI/CD intelligence system where code changes are analyzed to determine impacted test suites dynamically.

### 🧠 Purpose

This service is responsible for:

- Detecting changed files using Git diff (JGit)
- Mapping changes to test execution tags
- Exposing an API for CI pipelines to fetch impacted tests
- Enabling selective test execution instead of full regression
  
### 🏗 Architecture Role

```text 
Dev Repo (fintech-impact-services)
        │
        ├── Spring Boot Application
        ├── JGit Diff Analysis
        ├── Impact Mapping Engine
        ├── REST API (/test-selector)
        ▼
Test Automation Repo (karate-change-impact-test)
        ├── Calls API
        ├── Fetches impacted tags
        └── Runs selected Karate tests
```

### 🔁 Workflow

### 1. Git Change Detection

The service compares branches using JGit:

- Identifies changed files
- Extracts file paths from diff

### 2. Impact Mapping Logic

Files are mapped to test execution tags.

📊 Mapping Rules
Change Type	Test Tag
/payments/	@payments
/transactions/	@transactions
/transfer/	@transfers
/auth/	@regression
pom.xml	@regression
No match	@smoke

### 3. API Exposure

### Endpoint:

```text

HTTP GET /api/dev-ops/test-selector?targetBranch=main~1

```
###  Response Example

```text
Json

{
  "impacted_tags": "@payments,@transactions"
}

```

## ⚙️ How It Works Internally
- Uses JGit to compute diff between branches
- Extracts modified file paths
- Applies rule-based mapping engine
- Returns impacted test tags as JSON

## 🚀 Run Locally
Prerequisites
Java 17
Maven
Git

## Start Application

```text

bash

mvn spring-boot:run

```

## 🔍 Test API

```text
curl

http://localhost:8080/api/dev-ops/test-selector?targetBranch=main~1

```

## 📦 Tech Stack
- Java 17
- Spring Boot
- JGit
- Maven

## 📊 Key Benefits

- 🔁 Enables impact-based test selection
- ⏱ Reduces CI execution time
- 💰 Reduces regression cost
- 🧠 Supports intelligent test targeting in microservices

## 🧠 Design Highlights

- Git diff–based analysis engine
- Rule-based tag mapping
- CI/CD integration ready

##  ⚠️ Known Limitations

- Rule-based mapping (not ML-driven)
- Requires full git history for accurate diff
- Depends on branch comparison strategy

## 👨‍💻 Author

Designed and implemented a Git diff–based impact analysis service for intelligent test selection in CI/CD pipelines.
