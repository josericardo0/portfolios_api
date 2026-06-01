# 📊 Sistema de Gestão de Portfólio de Projetos

API REST desenvolvida com Spring Boot para gerenciamento do ciclo de vida de projetos, incluindo controle de status, classificação de risco, alocação de membros e geração de relatórios gerenciais.

## 🚀 Tecnologias Utilizadas

* Java 21
* Spring Boot 3.5.14
* Spring Data JPA
* Hibernate
* PostgreSQL
* OpenFeign
* Spring Validation
* MapStruct
* Lombok
* Swagger / OpenAPI
* Docker
* Docker Compose
* JUnit 5
* Mockito
* JaCoCo

## 📋 Pré-requisitos

* Docker
* Docker Compose
* Java 21 (opcional para execução local)
* Maven 3.9+ (opcional para execução local)

## 🐳 Executando com Docker

### Gerar o artefato da aplicação

```bash
mvn clean package
```

### Subir a aplicação e o banco de dados

```bash
docker compose up --build
```

## 🌐 Acessos

### API

```text
http://localhost:8080
```

### Swagger para consultar os endpoints disponíveis e ter base para os testes:

```text
http://localhost:8080/swagger-ui/index.html
```

### OpenAPI:

```text
http://localhost:8080/api-docs
```

### PostgreSQL:

```text
Host: localhost
Port: 5432
Database: projetos_db
User: postgres
Password: postgres
```

## 📦 Funcionalidades

* CRUD de projetos
* Controle de transição de status
* Classificação automática de risco
* Associação de membros via API mockada
* Relatório do portfólio de projetos
* Paginação de projetos
* Tratamento global de exceções
* Documentação Swagger/OpenAPI
* Testes unitários com JUnit


