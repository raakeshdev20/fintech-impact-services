![Status](https://img.shields.io/badge/status-proof--of--concept-orange)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.x-brightgreen)
![JGit](https://img.shields.io/badge/JGit-Git%20Diff%20Analysis-blue)

### 🚀 Fintech Impact Services (Dev Repository)

Spring Boot–based impact analysis service for intelligent CI test selection using Git diff analysis and JGit.

This repository acts as the orchestration and impact-analysis engine for a cross-repository selective testing framework.

### 📌 Overview

This repository contains the Fintech Spring Boot microservice that exposes an API for impact-based test selection.

It is part of a cross-repository CI/CD intelligence system where code changes are analyzed to determine impacted test suites dynamically.

### 📌 Project Status

This repository is a proof-of-concept (PoC) demonstrating
deterministic impact-aware CI execution using Git diff analysis.

The implementation was validated in controlled scenarios
to dynamically map code changes to impacted Karate test suites.

### ⚙️ Core Responsibilities

- Detect changed files using JGit diff analysis
- Map source code changes to Karate test tags
- Expose impact-analysis APIs for CI pipelines
- Enable selective test execution instead of full regression runs
  
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

## 📊 Mapping Rules

| Change Type      | Test Tag        |
|------------------|-----------------|
| /payments/       | @payments       |
| /transactions/   | @transactions   |
| /transfer/       | @transfers      |
| /auth/           | @regression     |
| pom.xml          | @regression     |
| No match         | @smoke          |

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

- 🔁 Enables deterministic impact-based test selection
- ⏱ Demonstrated CI runtime reduction in controlled scenarios
- 💰 Reduces unnecessary regression execution
- 🧠 Supports targeted test execution in microservice environments
- 🔗 Integrates with GitHub Actions–based CI workflows

## 🧠 Design Highlights

- Git diff–based analysis engine
- Rule-based tag mapping
- Designed for GitHub Actions–based CI integration

## 📌 Why This Matters

In microservice architectures, running full regression suites for every change creates CI bottlenecks and unnecessary compute overhead.

This project demonstrates a lightweight deterministic approach to impact-aware CI execution without requiring external dependency graph tooling or SaaS platforms.

##  ⚠️ Known Limitations

- Rule-based mapping (not ML-driven)
- Requires full git history for accurate diff
- Depends on branch comparison strategy

## 👨‍💻 Author

Designed and implemented a Git diff–based impact analysis service for intelligent test selection in CI/CD pipelines.
