package com.smartChart.reservation.repository;


import com.smartChart.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

 Reservation findByReservationDateAndReservationTimeAndDoctorId(Date reservationDate, Time reservationTime, int doctorId);


}
