
# 🏥 ProntoSocorro – Sistema de Atendimento Hospitalar

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-brightgreen)
![Maven](https://img.shields.io/badge/Maven-3.9-blue)
![Status](https://img.shields.io/badge/Status-Em%20Desenvolvimento-yellow)

Sistema de simulação de **atendimento em pronto-socorro hospitalar**, modelado com foco em **Domain-Driven Design (DDD)**, **boas práticas de OO**, **State Pattern** e **separação clara de responsabilidades**, antes da introdução de Controller, Repository e Banco de Dados.

---

## 📌 Visão Geral

Este projeto foi desenvolvido seguindo o princípio:

> **Domínio primeiro, infraestrutura depois**

A ideia central é modelar corretamente o **negócio hospitalar**, garantindo que regras clínicas, estados do paciente e fluxo de atendimento estejam bem definidos antes de qualquer integração com banco de dados ou API REST.

---

## 🧠 Arquitetura (Visão Conceitual)
src/main/java/com/emergencia/prontosocorro
│
├── Domain
│   ├── models
│   │   ├── CareStatus.java
│   │   ├── ComorbidityType.java
│   │   └── StatusType.java
│   │
│   ├── State
│   │   ├── Status
│   │   │   ├── Sick.java
│   │   │   ├── Live.java
│   │   │   └── Dead.java
│   │   ├── StatePatient.java
│   │   └── StatePeopleFactory.java
│   │
│   ├── FirstCare.java
│   ├── People.java
│   ├── Hospital.java
│   └── SpecialistMedic.java
│
├── Service
│   ├── CareService.java
│   ├── DecisionCaseCriticalService.java
│   └── RegretsMedicService.java
│
└── ProntosocorroApplication.java





Rodar o Maven no meu Linux
source ~/.sdkman/bin/sdkman-init.sh
Depois:

sdk version
sdk current java