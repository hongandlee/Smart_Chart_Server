package com.smartChart.reservation.repository;


import com.smartChart.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

  Reservation findByReservationDateAndReservationTimeAndDoctorId(Date reservationDate, Time reservationTime, int doctorId);


  // select  - ID
   Reservation findById(int reservationId);





  // 날짜, 시간, doctorId
  @Query(nativeQuery = true, value =  "select A.id, B.name, A.reservationDate, A.reservationTime, B.phoneNumber\n" +
          "from reservation AS A\n" +
          "join patient AS B\n" +
          "on A.patientId = B.id\n" +
          "where A.reservationDate = :reservationDate AND A.reservationTime = :reservationTime AND A.doctorId = :doctorId")
  public List<ReservationInterface> findReservationByReservationDateAndReservationTimeAndDoctorId(
          @Param("reservationDate") Date reservationDate,
          @Param("reservationTime") Time reservationTime,
          @Param("doctorId") int doctorId);





  // 리스트 - 날짜, 시간, doctorId
  @Query(nativeQuery = true, value =  "select A.id, B.name, A.reservationDate, A.reservationTime, B.phoneNumber\n" +
          "from reservation AS A\n" +
          "join patient AS B\n" +
          "on A.patientId = B.id\n" +
          "where A.reservationDate = :reservationDate AND A.doctorId = :doctorId")
  public List<ReservationInterface> findAllByReservationDateAndReservationTimeAndDoctorId(
          @Param("reservationDate") Date reservationDate,
          @Param("doctorId") int doctorId);



}
