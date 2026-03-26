
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


## 🐰 RabbitMQ

O sistema utiliza RabbitMQ para mensageria assíncrona.

### ▶️ Executar com Docker

```bash
docker start rabbitmq

docker run -d \
  --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3-management

🌐 Acessos
Aplicação (AMQP): amqp://localhost:5672

Painel Web: http://localhost:15672

Login:

user: guest

password: guest


🔧 Versão melhorada (pode copiar)
📊 Observabilidade
O projeto possui observabilidade básica e avançada, permitindo monitorar o comportamento das regras de negócio em tempo real.

🔹 Observabilidade Simples (Spring Boot Actuator)
Métricas são expostas via Spring Boot Actuator + Micrometer, sem necessidade de ferramentas externas.

▶️ Como acessar
Após iniciar a aplicação:

http://localhost:8080/actuator/metrics
🔎 Métricas de negócio disponíveis
patient.create.total → criação de paciente / atendimento inicial

patient.update.total → atualização do estado do paciente

patient.transfer.total → transferência entre setor/hospital

patient.death.total → registro de óbito

📌 Exemplo
http://localhost:8080/actuator/metrics/patient.transfer.total
🔥 Observabilidade Avançada (Prometheus)
O projeto também suporta integração com Prometheus, permitindo coleta contínua e análise histórica das métricas.

▶️ Pré-requisitos
Aplicação rodando (localhost:8080)

RabbitMQ ativo (necessário para o sistema)

📁 Arquivo prometheus.yml
global:
  scrape_interval: 5s

scrape_configs:
  - job_name: 'prontosocorro'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['host.docker.internal:8080']
▶️ Subir o Prometheus
docker run -d \
  -p 9090:9090 \
  --add-host=host.docker.internal:host-gateway \
  -v $(pwd)/prometheus.yml:/etc/prometheus/prometheus.yml \
  prom/prometheus
🌐 Acessos
Prometheus UI:

http://localhost:9090
🔎 Consultar métricas
No campo de busca do Prometheus:

patient_transfer_total
📈 Exemplo de retorno
patient_transfer_total{status="success"} 5