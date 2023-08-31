package com.smartChart.reservation.service;


import com.smartChart.doctor.entity.Doctor;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.entity.Patient;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationInterface;
import com.smartChart.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReservationRepository reservationRepository;

    private final PatientService patientService;




    // 예약 조회 by ID
    public Reservation findById(int reservationId) {
        return reservationRepository.findById(reservationId);
    }


    // 예약 가능 조회
    public Reservation findByReservationDateAndReservationTimeAndDoctorId (Date reservationDate, Time reservationTime, int doctorId) {
        return reservationRepository.findByReservationDateAndReservationTimeAndDoctorId(reservationDate, reservationTime, doctorId);
    }



    // 예약 날짜, 시간 조회
    public List<ReservationInterface> findReservationByReservationDateAndReservationTimeAndDoctorId(Date reservationDate, Time reservationTime, int doctorId) {
        return reservationRepository.findReservationByReservationDateAndReservationTimeAndDoctorId(reservationDate, reservationTime, doctorId);
    }

    // 예약 리스트 날짜, 시간 조회
    public List<ReservationInterface> findAllByReservationDateAndReservationTimeAndDoctorId(Date reservationDate, int doctorId) {
        return reservationRepository.findAllByReservationDateAndReservationTimeAndDoctorId(reservationDate,  doctorId);
    }

    // 예약하기
    public Reservation addReservation (Patient patientId, Doctor doctorId, Date reservationDate, Time reservationTime) {


            Reservation reservation = reservationRepository.save(
                    Reservation.builder()
                            .patient(patientId)
                            .doctor(doctorId)
                            .reservationDate(reservationDate)
                            .reservationTime(reservationTime)
                            .reservationStatus("미완료")
                            .paymentStatus("미완료")
                            .patientPaymentStatus("미완료")
                            .build());
            return reservation;

    }



    // update - 예약 확정 상태
    public Reservation updateReservationStatusById(int reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation != null) {
            reservation = reservation.toBuilder() // toBuilder는 기존값 유지하고 일부만 변경
                    .reservationStatus("완료")
                    .build();

            reservation = reservationRepository.save(reservation);

            logger.info("######### 예약 상태가 완료로 변경");
        }
        return reservation;

    }




}
