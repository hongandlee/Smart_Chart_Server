package com.smartChart.TreatmentServiceTest;


import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.service.ReservationService;
import com.smartChart.treatment.repository.TreatmentRepository;
import com.smartChart.treatment.service.TreatmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@SpringBootTest
public class TreatmentServiceTest {

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private ReservationService reservationService;



    //@Test
    //@DisplayName("진료 관리 저장")
    void add() {

        Doctor doctor = doctorService.findById(4);
        assertNotNull(doctor);
        Reservation reservation = reservationService.findById(32);
        assertNotNull(reservation);
        log.info("############### 테스트 진료관리 저₩");
        treatmentService.addTreatment(reservation, doctor, "과거병력", "주요증상", "현재증상", "치료계획", "비고");


    }
}
