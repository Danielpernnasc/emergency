package com.emergencia.prontosocorro;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.emergencia.prontosocorro.Domain.FirstCare;
import com.emergencia.prontosocorro.Domain.Hospital;
import com.emergencia.prontosocorro.Domain.People;
import com.emergencia.prontosocorro.Domain.State.Status.Sick;
import com.emergencia.prontosocorro.Domain.models.ComorbidityType;
import com.emergencia.prontosocorro.Service.CareService;
import com.emergencia.prontosocorro.Service.DecisionCaseCriticalService;

@SpringBootApplication
public class ProntosocorroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProntosocorroApplication.class, args);

	}

	@Bean
	public CommandLineRunner run() {
		return args -> {
			System.out.println("=== TESTE DO MAIN ===");

			CareService careService = new CareService();
		    DecisionCaseCriticalService decisionService = new DecisionCaseCriticalService();
	
			
			// People paciente = new People("João Silva", 30, "Dor no peito", new Sick());

			// // Hospital hospital = new Hospital("Hospital Central", "Rua Principal, 123", 100);

			// Hospital hospital = new Hospital(1L, "Hospital Central", "Rua Principal", 100);

			//  System.out.println("Paciente: " + paciente.getName());
            // System.out.println("Idade: " + paciente.getAge());
            // System.out.println("Descrição: " + paciente.getDescription());
            // System.out.println("Hospital: " + hospital.getNameHospital());
			// System.out.println("Endereço do Hospital: " + hospital.getAddress());
			// System.out.println("Número do Hospital: " + hospital.getNumero());
			People[] pacientes = {
					new People(
						"João Silva", 30, 
						"Dor no peito", 
						new Sick(), 
						new Hospital(1L, "Hospital Central", "Rua Principal", 100), 
						new ArrayList<>()
					),
					new People(
						"Maria Oliveira", 25, 
						"Fratura no braço", 
						new Sick(), 
						new Hospital(2L, "Hospital Norte", "Avenida Secundária", 200),
						new ArrayList<>()
					),
					new People(
						"Carlos Souza", 40, 
						"Febre alta", 
						new Sick(), 
						new Hospital(3L, "Hospital Sul", "Travessa Terciária", 300),
						new ArrayList<>()
					),
					new People(
						"Ana Lima", 35, 
						"Corte profundo", 
						new Sick(),  
						new Hospital(1L, "Hospital Central", "Rua Principal", 100),
						new ArrayList<>()
					),
					new People(
						"Pedro Santos", 28, 
						"Dificuldade respiratória", 
						new Sick(), 
						new Hospital(2L, "Hospital Norte", "Avenida Secundária", 200),
						new ArrayList<>()
					),
					new People(
						"Ciclano Sobrenome", 
						60, 
						"Infarto", 
						new Sick(), 
						new Hospital(2L, "Hospital São Lucas", 
						"Avenida Secundária", 200), 
						List.of(ComorbidityType.HIPERTENSAO, ComorbidityType.DIABETES, ComorbidityType.OBESIDADE)
					),
					new People(
						"Bertano Sobrenome", 45, 
						"Trauma perfurante no Pumão", 
						new Sick(), 
						new Hospital(3L, "Hospital Sul", "Travessa Terciária", 300),
						List.of(ComorbidityType.HIPERTENSAO, ComorbidityType.CARDIOPATIA)
					),
					new People(
						"Fulano de Tal", 50, 
						"Trauma perfurante no abdomen", 
						new Sick(), 
						new Hospital(1L, "Hospital Central", "Rua Principal", 100),
						List.of(ComorbidityType.DIABETES)
					)
				};

			try {
				
				for (People p : pacientes) {
					FirstCare assistance = careService.createFirstCare(p, p.getHospital());
					
					System.out.println("---------------------------");
					System.out.println("Paciente deu entrada no hospital...");
					System.out.println("Paciente: " + assistance.getPeople().getName());
					System.out.println("Idade: " + assistance.getPeople().getAge());
					System.out.println("Descrição: " + assistance.getPeople().getDescription());
					if(decisionService.isCriticalCase(assistance.getPeople())) {
						System.out.println("Comorbidade: " +
							(
								assistance.getPeople().getComorbidities().contains(ComorbidityType.HIPERTENSAO) ||
								assistance.getPeople().getComorbidities().contains(ComorbidityType.DIABETES) ||
								assistance.getPeople().getComorbidities().contains(ComorbidityType.OBESIDADE) ||
								assistance.getPeople().getComorbidities().contains(ComorbidityType.DOENCA_DE_CHAGAS)
							)
						);
					} else {
						System.out.println("Comorbidade: Não há");
					}
					System.out.println("Estado: " + assistance.getPeople().getStatePatient().toString());
					System.out.println("Hospital: " + assistance.getPeople().getHospital().getNameHospital());
					System.out.println("Endereço do Hospital: " + assistance.getPeople().getHospital().getAddress());
					System.out.println("Número do Hospital: " + assistance.getPeople().getHospital().getNumero());
					careService.dischargePatient(assistance);
					System.out.println( "Paciente: " + assistance.getPeople().getName() 
						+ (careService.canBeDiscarge(assistance.getPeople()) ? " foi liberado para alta." : " não pode ser liberado para alta.")
					);
					if(assistance.getPeople().getDescription().toLowerCase().contains("infarto")){
						assistance.getPeople().registerDeath("Infarto fulminante");
						System.out.println("Nome: " + assistance.getPeople().getName());
						System.out.println("Diagnóstico inicial: " + assistance.getPeople().getDescription());
						System.out.println(
							"❌ Paciente " + assistance.getPeople().getName()
							+ " não resistiu e veio a óbito em "
							+ assistance.getPeople().getDeathTime()
						);
					}
					// else {
					// 	   System.out.println(
					// 		"✅ Paciente " + assistance.getPeople().getName()
					// 		+ " resistiu e segue em atendimento."
					// 	);
					// } 


					if(assistance.getPeople().getName().equals("Bertano Sobrenome")){
						assistance.getPeople().registerDeath("Hemorragia interna por trauma perfurante no pulmão");

						System.out.println(
							"❌ Paciente " + assistance.getPeople().getName()
							+ " não resistiu e veio a óbito em "
							+ assistance.getPeople().getDeathTime()
						);

					}else if(assistance.getPeople().getName().equals("Fulano de Tal")) {
						System.out.println(
							"✅ Paciente " + assistance.getPeople().getName()
							+ " segue em atendimento."
						);
					}
				}
			} catch (IllegalArgumentException e) {
				System.out.println("Erro ao criar paciente: " + e.getMessage());
			}	
		};

	};

}
	


