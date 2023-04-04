package com.prabhu;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prabhu.patient.AppointmentServices;
import com.prabhu.patient.Patient;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

	@Spy
	private AppointmentServices appointmentService;


	@Test
	void test_appointment_date_in_past() {
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();

		LocalDateTime fixedDateTime = LocalDateTime.of(2023, 4, 2, 10, 00);
		try (MockedStatic<LocalDateTime> mockedLocalDateTime = Mockito.mockStatic(LocalDateTime.class)) {
			mockedLocalDateTime.when(LocalDateTime::now).thenReturn(fixedDateTime);

			appointmentService.bookAppointment(testPatient, fixedDateTime);
		}
	}

	@Test
	void test_appointment_date_not_in_past()
	{
		Patient testPatient = Patient.builder().firstName("John").lastName("Doe").dateOfBirth(LocalDate.now()).build();

		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			appointmentService.bookAppointment(testPatient, LocalDateTime.of(2023, 4, 2, 10, 00));

		});

		assertEquals("Appointment Datetime cannot be less than today date", exception.getMessage());

	}

}