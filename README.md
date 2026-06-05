# 📊 Sistema de Gestão de Portfólio de Projetos

API desenvolvida com Java e Spring Boot para CRUD e gerenciamento de projetos, incluindo controle de status, classificação de risco, alocação de membros e geração de relatórios.

## 🚀 Tecnologias utilizadas

* Java 21
* Spring Boot 3.5.14
* Spring Data JPA
* Spring Security
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

### Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

### PostgreSQL:

```text
Host: localhost
Port: 5432
Database: projetos_db
User: postgres
Password: postgres
```
## 🔐 Testando a API via Postman

### Autenticação

Os endpoints de **Projetos** e **Relatórios** estão protegidos com **HTTP Basic Authentication**.

#### Credenciais

```text
Username: admin
Password: 123456
```

### Configuração no Postman

1. Abra a aba **Authorization**
2. Selecione **Basic Auth**
3. Preencha:

```text
Username: admin
Password: 123456
```

---

## 1. Criar Membro

**Endpoint**

```http
POST /mock-api/membros/criar-membro
```

**URL**

```http
http://localhost:8080/mock-api/membros/criar-membro
```

**Autenticação**

```text
Não requerida
```

**Body**

```json
{
  "nome": "José Ricardo",
  "funcao": "FUNCIONARIO"
}
```

---

## 2. Buscar Membro

**Endpoint**

```http
GET /mock-api/membros/buscar-membro/{id}
```

**URL**

```http
http://localhost:8080/mock-api/membros/buscar-membro/1
```

**Autenticação**

```text
Não requerida
```

---

## 3. Criar Projeto

**Endpoint**

```http
POST /api/projetos/criar-projeto
```

**URL**

```http
http://localhost:8080/api/projetos/criar-projeto
```

**Autenticação**

```text
Basic Auth
```

**Body**

```json
{
  "nome": "Sistema Financeiro",
  "dataInicio": "2026-06-01",
  "dataTerminoPrevisto": "2026-09-01",
  "orcamentoTotal": 80000,
  "descricao": "Projeto de gestão financeira",
  "gerenteId": 2,
  "membrosIds": [1]
}
```

---

## 4. Buscar Projeto

**Endpoint**

```http
GET /api/projetos/buscar-projeto/{id}
```

**URL**

```http
http://localhost:8080/api/projetos/buscar-projeto/{uuid}
```

**Exemplo**

```http
http://localhost:8080/api/projetos/buscar-projeto/675e0c2f-5b7f-4ee7-b7c7-a75c62f94c20
```

**Autenticação**

```text
Basic Auth
```

---

## 5. Listar Projetos

**Endpoint**

```http
GET /api/projetos/listar-projetos
```

**URL**

```http
http://localhost:8080/api/projetos/listar-projetos
```

**Autenticação**

```text
Basic Auth
```

### Paginação

```http
http://localhost:8080/api/projetos/listar-projetos?page=0&size=10
```

---

## 6. Atualizar Projeto

**Endpoint**

```http
PUT /api/projetos/atualizar-projeto/{id}
```

**URL**

```http
http://localhost:8080/api/projetos/atualizar-projeto/{uuid}
```

**Autenticação**

```text
Basic Auth
```

**Body**

```json
{
  "nome": "Sistema Financeiro V2",
  "orcamentoTotal": 120000,
  "descricao": "Projeto atualizado"
}
```

---

## 7. Atualizar Status do Projeto

**Endpoint**

```http
PATCH /api/projetos/atualizar-status/{id}
```

**URL**

```http
http://localhost:8080/api/projetos/atualizar-status/{uuid}
```

**Autenticação**

```text
Basic Auth
```

**Body**

```json
{
  "statusProjeto": "ANALISE_REALIZADA"
}
```

### Fluxo permitido

```text
EM_ANALISE
↓
ANALISE_REALIZADA
↓
ANALISE_APROVADA
↓
INICIADO
↓
PLANEJADO
↓
EM_ANDAMENTO
↓
ENCERRADO
```

O status **CANCELADO** pode ser utilizado durante qualquer etapa do fluxo.

---

## 8. Excluir Projeto

**Endpoint**

```http
DELETE /api/projetos/excluir-projeto/{id}
```

**URL**

```http
http://localhost:8080/api/projetos/excluir-projeto/{uuid}
```

**Autenticação**

```text
Basic Auth
```

### Observação

Projetos com status:

```text
INICIADO
EM_ANDAMENTO
ENCERRADO
```

não podem ser excluídos.

---

## 9. Gerar Relatório

**Endpoint**

```http
GET /api/relatorios/gerar-relatorio
```

**URL**

```http
http://localhost:8080/api/relatorios/gerar-relatorio
```

**Autenticação**

```text
Basic Auth
```

### Exemplo de resposta

```json
{
  "projetosPorStatus": {
    "EM_ANALISE": 2,
    "EM_ANDAMENTO": 1
  },
  "orcamentoPorStatus": {
    "EM_ANALISE": 180000,
    "EM_ANDAMENTO": 250000
  },
  "mediaDuracaoProjetosEncerrados": 90.0,
  "membrosAssociados": 5
}
```

---

## Fluxo recomendado para testes

1. Criar um membro.
2. Buscar o membro criado.
3. Criar um projeto utilizando um membro como gerente e um ou mais membros como integrantes.
4. Buscar o projeto criado.
5. Atualizar os dados do projeto.
6. Atualizar o status do projeto.
7. Listar projetos.
8. Gerar relatório.
9. Excluir o projeto (quando permitido pelas regras de negócio).


## 📦 Funcionalidades

* CRUD de projetos
* Autenticação via Spring Security
* Controle de transição de status
* Classificação automática de risco
* Associação de membros via API mockada
* Relatório do portfólio de projetos
* Paginação de projetos
* Tratamento global de exceções
* Documentação Swagger/OpenAPI
* Testes unitários com JUnit


