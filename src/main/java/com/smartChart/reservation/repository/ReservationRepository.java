package com.smartChart.reservation.repository;


import com.smartChart.reservation.entity.Reservation;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
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
  @Query(nativeQuery = true, value =  "select A.id, B.name, A.patientId, A.reservationDate, A.reservationTime, B.phoneNumber, A.reservationStatus, A.paymentStatus\n" +
          "from reservation AS A\n" +
          "join patient AS B\n" +
          "on A.patientId = B.id\n" +
          "where A.reservationDate = :reservationDate AND A.reservationTime = :reservationTime AND A.doctorId = :doctorId")
  public List<ReservationInterface> findReservationByReservationDateAndReservationTimeAndDoctorId(
          @Param("reservationDate") Date reservationDate,
          @Param("reservationTime") Time reservationTime,
          @Param("doctorId") int doctorId);





  // 리스트 - 날짜, 시간, doctorId
  @Query(nativeQuery = true, value =  "select A.id, B.name, A.patientId, A.reservationDate, A.reservationTime, B.phoneNumber, A.reservationStatus, A.paymentStatus\n" +
          "from reservation AS A\n" +
          "join patient AS B\n" +
          "on A.patientId = B.id\n" +
          "where A.reservationDate = :reservationDate AND A.doctorId = :doctorId")
  public List<ReservationInterface> findAllByReservationDateAndReservationTimeAndDoctorId(
          @Param("reservationDate") Date reservationDate,
          @Param("doctorId") int doctorId);





  // 최신 예약건 10개
  @Query(nativeQuery = true, value =  "select A.id, B.name,A.patientId, A.reservationDate, A.reservationTime, B.phoneNumber, A.reservationStatus, A.paymentStatus\n" +
          "from reservation AS A\n" +
          "join patient AS B\n" +
          "on A.patientId = B.id\n" +
          "where A.doctorId = :doctorId\n"
          + "order by A.id desc LIMIT 10")
  public List<ReservationInterface> findByDoctorIdOrderByIdDesc(
          @Param("doctorId") int doctorId);



  // 진료 관리 조회
  @Query(nativeQuery = true, value =  "select B.id, A.hospitalName, B.reservationDate, C.name, C.phoneNumber, C.gender, C.age\n" +
          "from reservation AS B\n" +
          "join doctor AS A\n" +
          "on B.doctorId = A.id\n" +
          "join patient AS C\n" +
          "on C.id = B.patientId\n" +
          "where B.id = :reservationId"
  )
  public List<DoctorTreatmentInterface> findByReservationId(
          @Param("reservationId") int reservationId);






}
