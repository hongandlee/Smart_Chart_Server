package com.smartChart.cost.repository;

import com.smartChart.cost.dto.*;
import com.smartChart.cost.entity.Treatment_statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TreatmentStatementRepository extends JpaRepository<Treatment_statement, Integer> {


    // 예약 테이블, doctor 테이블(병원명), patient 테이블, treatment_statement 테이블(총금액), reservation테이블- patientPaymentStatus,
    // by patientId
    @Query(nativeQuery = true, value =  "select B.id, A.hospitalName, B.reservationDate, C.name, sum(D.cost) as sum,B.patientPaymentStatus\n" +
            "from reservation AS B\n" +
            "join doctor AS A\n" +
            "on B.doctorId = A.id\n" +
            "join patient AS C\n" +
            "on C.id = B.patientId\n" +
            "join treatment_statement AS D\n" +
            "on D.reservationId = B.id\n" +
            "where B.patientId = :patientId\n" +
            "GROUP BY B.id\n" +
            "order by B.id desc")
    public List<PatientCostInterface> findByPatientId(
            @Param("patientId") int patientId);




    // 월별 매출
    @Query(nativeQuery = true, value =  " SELECT MONTH(B.reservationDate) AS `MONTH`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `MONTH`;")

    public List<DoctorMonthInterface> findByDoctorId(
            @Param("doctorId") int doctorId);



    // 년별 매출
    @Query(nativeQuery = true, value =  "SELECT YEAR(B.reservationDate) AS `YEAR`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR`")

    public List<DoctorYearInterface> findYearByDoctorId(
            @Param("doctorId") int doctorId);



    // 주간 매출
    @Query(nativeQuery = true, value =  "SELECT DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-1) DAY), '%Y-%m-%d') as start,\n" +
            "       DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-7) DAY), '%Y-%m-%d') as end,\n" +
            "  sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY start, end")

    public List<DoctorWeekInterface> findWeekByDoctorId(
            @Param("doctorId") int doctorId);



    // 일별 매출
    @Query(nativeQuery = true, value =  "SELECT DATE(B.reservationDate) AS `DATE`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `DATE`;")

    public List<DoctorDateInterface> findDateByDoctorId(
            @Param("doctorId") int doctorId);





    // 기간별 매출
    @Query(nativeQuery = true,
            value =  "SELECT DATE(B.reservationDate) AS `DATE`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
                    "  FROM `reservation` AS B\n" +
                    "  join `treatment_statement` AS A\n" +
                    "  on B.id = A.reservationId\n" +
                    "  join `patient` AS C\n" +
                    "  on B.patientId = C.id\n" +
                    "  where A.doctorId = :doctorId\n" +
                    "\t\tAND DATE(B.reservationDate) >= STR_TO_DATE(:startDate, '%Y-%m-%d')\n" +
                    "\t\tAND DATE(B.reservationDate) <= STR_TO_DATE(:endDate, '%Y-%m-%d')\n" +
                    "  GROUP BY `DATE`")

    public List<DoctorPeriodInterface> findPeriodByDoctorIdAndStartDateAndEndDate(
            @Param("doctorId") int doctorId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
