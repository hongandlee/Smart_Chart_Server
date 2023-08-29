package com.smartChart.reservation.service;


import com.smartChart.doctor.entity.Doctor;
import com.smartChart.patient.entity.Patient;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationRepository reservationRepository;


    // 예약 가능 조회
    public Reservation findByReservationDateAndReservationTimeAndDoctorId (Date reservationDate, Time reservationTime, int doctorId) {
        return reservationRepository.findByReservationDateAndReservationTimeAndDoctorId(reservationDate, reservationTime, doctorId);
    }

    // 예약하기
    public Reservation addReservation (Patient patientId, Doctor doctorId, Date reservationDate, Time reservationTime) {


            Reservation reservation = reservationRepository.save(
                    Reservation.builder()
                            .patient(patientId)
                            .doctor(doctorId)
                            .reservationDate(reservationDate)
                            .reservationTime(reservationTime)
                            .build());
            return reservation;

    }
}
