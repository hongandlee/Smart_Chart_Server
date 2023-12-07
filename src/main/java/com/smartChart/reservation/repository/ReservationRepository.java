package com.smartChart.reservation.repository;


import com.smartChart.reservation.dto.DoctorTreatmentInterface2;
import com.smartChart.reservation.dto.ReservationInterface;
import com.smartChart.reservation.dto.WaitingInterface;
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
//  @Query(nativeQuery = true, value =  "select B.id, A.hospitalName, B.reservationDate, C.name, C.phoneNumber, C.gender, C.age, D.treatment, D.cost, sum(D.cost) as sum\n" +
//          "from reservation AS B\n" +
//          "join doctor AS A\n" +
//          "on B.doctorId = A.id\n" +
//          "join patient AS C\n" +
//          "on C.id = B.patientId\n" +
//          "join treatment_statement AS D\n" +
//          "on D.reservationId = B.id\n" +
//          "where B.id = :reservationId"
//  )
//  public List<DoctorTreatmentInterface> findByReservationId(
//          @Param("reservationId") int reservationId);

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




  // 진료 관리 조회 2 - 과거 진료비, 총합
  @Query(nativeQuery = true, value =
          "select D.id, D.treatment, D.cost,\n" +
                  "(select sum(D2.cost) from treatment_statement D2 where D2.reservationId = B.id) as sum\n " +
                  "from reservation AS B\n" +
                  "join treatment_statement AS D on D.reservationId = B.id\n " +
                  "where B.id = :reservationId")
  public List<DoctorTreatmentInterface2> findByReservationId2(
          @Param("reservationId") int reservationId);










  @Query(nativeQuery = true, value =  "select A.id, B.name,A.patientId, A.reservationDate, A.reservationTime, B.phoneNumber, A.reservationStatus, A.paymentStatus\n" +
            "from reservation AS A\n" +
            "join patient AS B\n" +
            "on A.patientId = B.id\n" +
            "where A.doctorId = :doctorId\n"
            + "order by A.id desc")
    public List<ReservationInterface> findByPatientIdOrderByIdDesc(
            @Param("doctorId") int doctorId);




  // 환자 마이페이지 예약 리스트 조회
  @Query(nativeQuery = true, value =  "select B.id, A.hospitalName, B.reservationDate, B.reservationTime, B.reservationStatus\n" +
          "from reservation AS B\n" +
          "join doctor AS A\n" +
          "on B.doctorId = A.id\n" +
          "join patient AS C\n" +
          "on C.id = B.patientId\n" +
          "where C.id = :patientId"
  )
  public List<DoctorTreatmentInterface> finListdByPatientId(
          @Param("patientId") int patientId);






 // 환자 대기 관리 조회
//  @Query(value = "SELECT A.id, A.reservationDate AS reservationDate, A.reservationTime, B.name AS patientName\n" +
//          "FROM reservation A\n" +
//          "LEFT join patient AS B\n" +
//          "on B.id = A.patientId\n" +
//          "WHERE A.reservationDate = (SELECT MAX(reservationDate) FROM reservation WHERE doctorId = :doctorId)", nativeQuery = true)
//  List<WaitingInterface> findRecentReservations(
//          @Param("doctorId") int doctorId);


  // 환자 대기 관리 조회
  @Query(value = "SELECT A.id, A.reservationDate AS reservationDate, A.reservationTime,A.waitingStatus, B.name AS patientName\n" +
          "FROM reservation A\n" +
          "LEFT join patient AS B\n" +
          "on B.id = A.patientId\n" +
          "WHERE A.doctorId = :doctorId AND A.reservationDate >= CURRENT_DATE AND A.reservationDate < CURRENT_DATE + INTERVAL 1 DAY;", nativeQuery = true)
  List<WaitingInterface> findRecentReservations(@Param("doctorId") int doctorId);

}
