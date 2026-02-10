// package com.emergencia.prontosocorro.service;

// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.List;

// import org.junit.jupiter.api.Test;

// import com.emergencia.prontosocorro.Domain.FirstCare;
// import com.emergencia.prontosocorro.Domain.Hospital;
// import com.emergencia.prontosocorro.Domain.People;
// import com.emergencia.prontosocorro.Domain.models.CareStatus;
// import com.emergencia.prontosocorro.Domain.models.CareofPacients;
// import com.emergencia.prontosocorro.Domain.models.SpecialistMedic;

// class CareServiceTest {

// @Test
// void deniesDischargeForGraveCasesEvenWithProcedures() {
// CareService service = new CareService(null, null, null, null);
// FirstCare firstCare = buildFirstCare("infarto", CareofPacients.MEDICACAO);

// boolean result = service.canBeDiscarged(firstCare.getPeople(), firstCare);

// assertFalse(result);
// }

// @Test
// void allowsDischargeForNonGraveWithProcedures() {
// CareService service = new CareService(null, null, null, null);
// FirstCare firstCare = buildFirstCare("corte profundo",
// CareofPacients.CURATIVO);

// boolean result = service.canBeDiscarged(firstCare.getPeople(), firstCare);

// assertTrue(result);
// }

// @Test
// void deniesDischargeForNonGraveWithoutProcedures() {
// CareService service = new CareService(null, null, null, null);
// FirstCare firstCare = buildFirstCare("corte profundo", null);

// boolean result = service.canBeDiscarged(firstCare.getPeople(), firstCare);

// assertFalse(result);
// }

// private static FirstCare buildFirstCare(String description, CareofPacients
// procedure) {
// Hospital hospital = new Hospital(null, "Hospital Central", "Rua A", 10);
// People people = new People("Paciente", 30, description, hospital, List.of());
// FirstCare firstCare = new FirstCare(people, hospital,
// SpecialistMedic.CLINICAL_MEDICINE,
// CareStatus.EM_ATENDIMENTO);

// if (procedure != null) {
// firstCare.getProcedures().add(procedure);
// }

// return firstCare;
// }
// }
