
# 🏥 ProntoSocorro – Sistema de Atendimento Hospitalar

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

🏥 ProntoSocorro – Sistema de Atendimento Hospitalar
## modelado com foco em: **Domain-Driven Design (DDD)**, **boas práticas de OO**, **State Pattern** e **separação clara de responsabilidades**, antes da introdução de Controller, Repository e Banco de Dados.

---

📌 Visão Geral
O ProntoSocorro é um sistema de atendimento hospitalar desenvolvido com foco em:

🧠 Domain-Driven Design (DDD)

🧩 Boas práticas de orientação a objetos

🔄 Arquitetura orientada a eventos (Event-Driven)

🏥 Modelagem realista de fluxo hospitalar

Domínio primeiro, infraestrutura depois

---

## 🧠 Arquitetura (Visão Conceitual)
src/main/java/com/emergencia/prontosocorro
│
├── Controller
│   ├── FirstCareController.java
│   ├── HospitalController.java
│   └── PeopleController.java
│
├── Domain
│   ├── Entity
│   │   ├── Cid.java
│   │   ├── CIDKeywordRule.java
│   │   ├── FirstCare.java
│   │   ├── Hospital.java
│   │   └── People.java
│   │
│   ├── enums
│   │   ├── CareofPacients.java
│   │   ├── CareStatus.java
│   │   ├── ComorbidityType.java
│   │   ├── SeverityLevel.java
│   │   ├── SpecialistMedic.java
│   │   └── StatusType.java
│   │
│   └── State
│       ├── Status
│       │   ├── Critical.java
│       │   ├── Dead.java
│       │   ├── Interned.java
│       │   ├── Sick.java
│       │   └── Urgent.java
│       │
│       ├── StatePatient.java
│       └── StatePeopleFactory.java
│
├── DTO
│   ├── Request
│   │   ├── DeathRequest.java
│   │   ├── FirstCareRequest.java
│   │   ├── HospitalRequest.java
│   │   ├── PeopleRequest.java
│   │   ├── StateEvolutionRequest.java
│   │   └── StatePatientRequest.java
│   │
│   ├── Response
│       ├── FirstCareResponse.java
│       └── PeopleResponse.java
│   
└── Message
│       ├── config
│       │   └── RabbitMQConfig.java
│       │
│       ├── consumer
│       │   ├── HospitalEventConsumer.java
│       │   └── SectorEventConsumer.java
│       │
│       ├── event
│       │   ├── PatientTransferredEvent.java
│       │   └── SectorChangedEvent.java
│       │
│       └── producer
│           └── HospitalEventProducer.java
│
├── Repository
│   ├── LoaderRepository
│   │   ├── RepositoryCID.java
│   │   └── RepositoryCIDKeywordRule.java
│   │
│   ├── RepositoryFirstCare.java
│   ├── RepositoryHospital.java
│   └── RepositoryPeople.java
│
├── Service
│   ├── CareService.java
│   ├── DeathService.java
│   └── PeopleService.java
│
└── ProntosocorroApplication.java

# 📐 Padrões de Projeto Utilizados

O projeto utiliza alguns padrões clássicos de engenharia de software:

### State Pattern
Utilizado para modelar o estado do paciente.

Estados possíveis:

- Sick
- Urgent
- Critical
- Interned
- Dead

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

A cobertura atual está acima de **80%**, garantindo maior confiabilidade do sistema.

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

🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Maven
- JUnit 5
- Mockito
- JaCoCo

🧩 Arquitetura de Mensageria (RabbitMQ)
O sistema utiliza RabbitMQ para comunicação assíncrona entre componentes.

🔄 Fluxo de Eventos
Service → Producer → RabbitMQ → Consumer → Service

## 🌐 Endpoints Principais


### Pacientes
POST /people  
GET /people  
GET /people/{id}

### Hospitais
POST /hospital  
GET /hospital

### Atendimento
POST /first-care  
POST/ first-care/transfer
PUT /first-care/{id}/add-comorbidity  
PUT /first-care/{id}/register-death
PATCH /first-care/{id}/sector


🏁 Conclusão
Este projeto prioriza qualidade de modelagem, clareza de domínio e arquitetura limpa, servindo como base sólida para evolução com API REST, persistência e integrações futuras.

▶️ Como Executar o Projeto
Clone o repositório:

git clone https://github.com/Danielpernnasc/emergencia
Entre na pasta do projeto:

cd prontosocorro
Execute o projeto:

mvn spring-boot:run
