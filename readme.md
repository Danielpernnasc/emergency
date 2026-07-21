
# 🏥 Emergency – Hospital Care System
# 🏥 ProntoSocorro – Sistema de Atendimento Hospitalar

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

## modelado com foco em: **Domain-Driven Design (DDD)**, **boas práticas de OO**, **State Pattern** e **separação clara de responsabilidades**, antes da introdução de Controller, Repository e Banco de Dados.

## modeled with focus in: *Domain-Driven Design (DDD)**, **good OO practices**, **State Pattern** and **clear separate of responsabilities**, before of the introduction of Controller, Repository and DataBase.

---

📌 Visão Geral
O sistema foi desenvolvido com foco em:

🧠 Domain-Driven Design (DDD)

🧩 Boas práticas de orientação a objetos

🔄 Arquitetura orientada a eventos (Event-Driven)

🏥 Simulação realista de fluxo hospitalar

---

## 🧠 Arquitetura (Visão Conceitual)

src/main/java/com/emergency/emergencyroom
│
├── config -> configuracao para CORS nao bloquear a api
|    ├──CorsConfig.java
|    └──OpenApiConfig.java
|
├── controller → Camada de entrada (API REST)
│   ├── FirstCareController.java
│   ├── HospitalController.java
│   └── PeopleController.java
│
├── domain → Núcleo do sistema (regras de negócio)
│   ├── entity → Entidades do domínio (não são apenas banco)
│   │   ├── Cid.java
│   │   ├── CIDKeywordRule.java
│   │   ├── FirstCare.java
│   │   ├── Hospital.java
│   │   └── People.java
│   │
│   ├── enums → Tipos de domínio (imutáveis)
│   │   ├── CareofPacients.java
│   │   ├── CareStatus.java
│   │   ├── ComorbidityType.java
│   │   ├── SeverityLevel.java
│   │   ├── SpecialistMedic.java
│   │   └── StatusType.java
│   │
│   └──state → Implementação do State Pattern
│       ├── status
│       │   ├── Critical.java
│       │   ├── Dead.java
│       │   ├── Interned.java
│       │   ├── Sick.java
│       │   └── Urgent.java
│       │
│       ├── StatePatient.java
│       └── StatePeopleFactory.java
│
├── DTO → Objetos de transferência de dados (entrada/saída da API)
│   ├── request → Dados recebidos pela API
│   │   ├── DeathRequest.java
│   │   ├── FirstCareRequest.java
│   │   ├── HospitalRequest.java
│   │   ├── PeopleRequest.java
│   │   ├── StateEvolutionRequest.java
│   │   └── StatePatientRequest.java
│   │
│   ├── response → Dados retornados pela API
│       ├── FirstCareResponse.java
│       └── PeopleResponse.java
│   
|
├──infra       
|   ├── config
│   │   ├── RabbitMQConfig.java  
│   │   └── CsvLoader.java
│   │ 
│   ├── messaging
│       │──consumer    
│       │   ├──CaresStatusEventConsumer.java
│       │   └── HospitalEventConsumer.java
│       │   └── SectorEventConsumer.java
│       │
│       ├── event
│       │   ├── PatientTransferredEvent.java
│       │   └── SectorChangedEvent.java
│       │
│       └── producer
│           └── HospitalEventProducer.java
│
├── service → Orquestração das regras de negócio
│   ├── CareService.java
│   ├── DeathService.java
│   └── PeopleService.java
│
├── repository → Persistência (acesso a dados)
│   ├── LoaderRepository
│   │   ├── RepositoryCID.java
│   │   └── RepositoryCIDKeywordRule.java
│   │
│   ├── RepositoryFirstCare.java
│   ├── RepositoryHospital.java
│   └── RepositoryPeople.java
│
└── infra → Infraestrutura (detalhes técnicos)
│   ├── config
│   │   └── RabbitMQConfig.java
│   │
│   ├── messaging → consumidores (entrada de eventos)
│   │   ├── HospitalEventConsumer.java
│   │   └── SectorEventConsumer.java
│   │
│   ├── producer → saída de eventos
│   │   └── HospitalEventProducer.java
│   │
│   ├── event → contratos de eventos
│       │   ├── PatientTransferredEvent.java
│       │   └── SectorChangedEvent.java
│       │
│       └── observability → métricas de negócio com Micrometer (monitoramento e análise do sistema)
│            └── ObservabilityService.java
│
└── ProntosocorroApplication.java

# 📐 Padrões de Projeto Utilizados

O projeto utiliza alguns padrões clássicos de engenharia de software:

🎯 Padrões Utilizados
State Pattern
Gerenciamento do estado do paciente:

Sick

Urgent

Critical

Interned

Dead


Cada estado define comportamentos específicos do paciente

Paciente → Atendimento → Hospital
            ↓
         Estado

---

### Factory Pattern

A criação dos estados do paciente é centralizada na classe:

# 📊 Cobertura de Testes

A cobertura de testes é analisada utilizando **JaCoCo**.

Principais áreas cobertas:

- Regras de negócio do domínio
- Fluxos de atendimento
- Registro de óbito
- Estados do paciente

🧪 Testes
✔ JUnit 5

✔ Mockito

✔ JaCoCo

📊 Cobertura: +80%



🧱 Arquitetura
controller → entrada (REST API)
service → regras de negócio
domain → núcleo do sistema
repository → persistência
infra → mensageria + config


# 📬 Exemplo de Requisição

Criar paciente:

POST /people

```json
{
  "name": "Arthur de Camelot",
  "idade": 45,
  "description": "Paciente com dor no peito",
  "comorbidities": ["OUTRA"]
}


🧠 Princípios Aplicados
✔️ Single Responsibility Principle (SRP)

✔️ Domain-Driven Design (DDD)

✔️ State Pattern

✔️ Separation of Concerns

✔️ Código preparado para evolução

🧰 Tecnologias
Java 21

Spring Boot

Spring Data JPA

Maven

RabbitMQ

Docker

Micrometer + Actuator
🧩 Arquitetura de Mensageria (RabbitMQ)
O sistema utiliza RabbitMQ para comunicação assíncrona entre componentes.

🔄 Fluxo de Eventos
Service → Producer → RabbitMQ → Consumer → Service

## 🌐 Endpoints Principais


📡 Endpoints
👤 Pacientes
POST /people

GET /people

GET /people/{id}

PUT /people/{id}/state/mistake

🏥 Hospital
POST /hospital

PUT /hospital/{io}

GET /hospital

🚑 Atendimento

POST /first-care

POST /first-care/transfer

PATCH /first-care/{id}/sector

PUT /first-care/{id}/add-comorbidity

PUT/first-care/{id}/evolution

PUT /first-care/{id}/register-death


---

# 🏁 Conclusão

O **Emergency Room** foi desenvolvido com foco em qualidade de código, clareza de domínio e arquitetura limpa, servindo como base sólida para evolução com API REST, persistência de dados, mensageria e integrações futuras.

---

# ▶️ Como Executar o Projeto

### 📥 1. Clone o repositório

```bash
git clone https://github.com/Danielpernnasc/emergency.git
cd emergency
```

### ⚙️ 2. Configure as variáveis de ambiente

Crie um arquivo `.env` na raiz do projeto (ou copie o `.env.example`, caso exista) e informe as credenciais necessárias para o banco de dados e RabbitMQ.

### 🔨 3. Compile a aplicação

```bash
mvn clean package
```

### 🐳 4. Inicie a aplicação

```bash
docker compose up -d --build
```

O Docker Compose iniciará automaticamente:

- ☕ Aplicação Spring Boot
- 🗄️ MySQL
- 🐰 RabbitMQ

---

# 🌐 Acessos

### 📘 Swagger

```
http://localhost:8080/swagger-ui/index.html
```

### 🐰 RabbitMQ Management

```
http://localhost:15672
```

Login padrão:

```
Usuário: guest
Senha: guest
```

---

## 🐰 RabbitMQ

O projeto utiliza **RabbitMQ** para comunicação assíncrona entre os serviços.

### 🔄 Fluxo de Mensageria

```
Service
   │
   ▼
Producer
   │
   ▼
RabbitMQ
   │
   ▼
Consumer
   │
   ▼
Service
```

> 💡 **Observação:** quando a aplicação é executada com **Docker Compose**, o RabbitMQ é iniciado automaticamente.

---

# 🗄️ Banco de Dados

O projeto utiliza **MySQL** como banco de dados relacional.

> 💡 As credenciais de acesso são configuradas no arquivo `.env`.

---

# 📊 Observabilidade

O projeto possui observabilidade utilizando:

- 📈 Spring Boot Actuator
- 📊 Micrometer
- 🔥 Prometheus

### 🔎 Endpoints

```
http://localhost:8080/actuator/health
```

```
http://localhost:8080/actuator/metrics
```

```
http://localhost:8080/actuator/prometheus
```

### 📈 Métricas disponíveis

- patient.create.total
- patient.update.total
- patient.transfer.total
- patient.death.total

---

# 🔥 Prometheus

Para coletar as métricas da aplicação:

```bash
docker run -d \
  -p 9090:9090 \
  --add-host=host.docker.internal:host-gateway \
  -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
```

### 🌐 Acesso

```
http://localhost:9090
```

---

#---

## 💻 Executando pelo IntelliJ IDEA ou VS Code
## ▶️ Executando o projeto

### Opção 1 - Executar pelo IntelliJ IDEA ou VS Code

Como a aplicação depende do **MySQL** e do **RabbitMQ**, esses serviços devem estar em execução antes de iniciar a aplicação.

Inicie apenas os serviços de infraestrutura:

```bash
docker compose up -d mysql rabbitmq
```

Verifique se os containers estão ativos:

```bash
docker ps
```

Em seguida, execute a classe `EmergencyRoomApplication` pelo IntelliJ IDEA ou VS Code.

> **Observação:** Ao executar a aplicação fora do Docker, configure as variáveis de ambiente para utilizar `localhost` como host do MySQL e do RabbitMQ.

Exemplo:

```properties
DB_URL=jdbc:mysql://localhost:3306/prontosocorro?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=emergency
DB_PASSWORD=emergency

SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
SPRING_RABBITMQ_VIRTUAL_HOST=/
SPRING_RABBITMQ_SSL=false
```

### Opção 2 - Executar totalmente com Docker

Para executar toda a aplicação em containers (Spring Boot, MySQL e RabbitMQ):

```bash
docker compose up -d --build
```

A aplicação ficará disponível em:

- Swagger: `http://localhost:8080/swagger-ui/index.html`
- RabbitMQ Management: `http://localhost:15672`
> **Observação:** Para executar a aplicação pelo IntelliJ IDEA ou VS Code, utilize um arquivo `.env.local` (baseado no `.env.example`) configurado para acessar o MySQL e o RabbitMQ em `localhost`.

## ⚙️ Arquivos de ambiente

O projeto utiliza diferentes arquivos de configuração conforme o ambiente de execução.

| Arquivo | Utilização |
|---------|------------|
| `.env` | Utilizado pelo Docker Compose. |
| `.env.local` | Utilizado ao executar a aplicação pelo IntelliJ IDEA ou VS Code. |
| `.env.example` | Modelo para criação dos arquivos de ambiente. |

> **Importante:** Ao executar a aplicação pela IDE, utilize um `.env.local` baseado no `.env.example`, configurando `localhost` como host do MySQL e do RabbitMQ.
# 👨‍💻 Autor

**Daniel P. Nascimento**

Backend Java Developer

- ☕ Java 21
- 🌱 Spring Boot
- 🏗️ Domain-Driven Design (DDD)
- 🐳 Docker
- 🐰 RabbitMQ
- 🗄️ MySQL

GitHub:
https://github.com/Danielpernnasc/emergency