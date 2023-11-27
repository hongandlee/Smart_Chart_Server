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
    @Query(nativeQuery = true, value =  " SELECT CONCAT(YEAR(B.reservationDate), '-', LPAD(MONTH(B.reservationDate), 2, '0')) AS `YEAR_MONTH`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR_MONTH`")

    public List<DoctorMonthInterface> findByDoctorId(
            @Param("doctorId") int doctorId);



    // 월별 매출 최신순
    @Query(nativeQuery = true, value =  " SELECT CONCAT(YEAR(B.reservationDate), '-', LPAD(MONTH(B.reservationDate), 2, '0')) AS `YEAR_MONTH`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR_MONTH`\n" +
            "  ORDER BY `YEAR_MONTH` DESC;\n")

    public List<DoctorMonthInterface> findRecentByDoctorId(
            @Param("doctorId") int doctorId);




    //##### 월별 성별 수
    @Query(nativeQuery = true, value =  " SELECT CONCAT(YEAR(B.reservationDate), '-', LPAD(MONTH(B.reservationDate), 2, '0')) AS `YEAR_MONTH`,\n" +
            "SUM(A.cost) AS `SUM`,\n" +
            "COUNT(DISTINCT B.id) AS `patientCount`,\n" +
            "COUNT(DISTINCT CASE WHEN C.gender = '남자' THEN B.id END) AS `MaleCount`,\n" +
            "COUNT(DISTINCT CASE WHEN C.gender = '여자' THEN B.id END) AS `FemaleCount`,\n" +
            "AVG(C.age) AS `averageAge`\n" +
            "FROM `reservation` AS B\n" +
            "JOIN `treatment_statement` AS A ON B.id = A.reservationId\n" +
            "JOIN `patient` AS C ON B.patientId = C.id\n" +
            "WHERE A.doctorId = :doctorId\n" +
            "GROUP BY `YEAR_MONTH`")

    public List<DoctorGenderMonthInterface> findGenderByDoctorId(
            @Param("doctorId") int doctorId);




    // 월별 매출이 많은 순서
    @Query(nativeQuery = true, value =
            "SELECT CONCAT(YEAR(B.reservationDate), '-', LPAD(MONTH(B.reservationDate), 2, '0')) AS `YEAR_MONTH`, SUM(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount` " +
                    "FROM `reservation` AS B " +
                    "JOIN `treatment_statement` AS A ON B.id = A.reservationId " +
                    "JOIN `patient` AS C ON B.patientId = C.id " +
                    "WHERE A.doctorId = :doctorId " +
                    "GROUP BY `YEAR_MONTH` " +
                    "ORDER BY `SUM` DESC") // SUM 열을 기준으로 내림차순 정렬
    public List<DoctorMonthInterface> findSalesByDoctorId(@Param("doctorId") int doctorId);





    // 월별 평균 환자 나이
    @Query(nativeQuery = true, value =  " SELECT CONCAT(YEAR(B.reservationDate), '-', LPAD(MONTH(B.reservationDate), 2, '0')) AS `YEAR_MONTH`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`, \n" +
            "AVG(C.age) AS `averageAge` " +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR_MONTH`")

    public List<DoctorAgeMonthInterface> findAgeMonthByDoctorId(
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






    // 년별 매출 최신순
    @Query(nativeQuery = true, value =  "SELECT YEAR(B.reservationDate) AS `YEAR`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR`\n" +
            "  ORDER BY `YEAR` DESC")

    public List<DoctorYearInterface> findRecentYearByDoctorId(
            @Param("doctorId") int doctorId);




    // 년별 매출이 많은 순
    @Query(nativeQuery = true, value =  "SELECT YEAR(B.reservationDate) AS `YEAR`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `YEAR`"+
            "  ORDER BY `SUM` DESC")

    public List<DoctorYearInterface> findYearSalesByDoctorId(
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




    // 주간 매출 최신순
    @Query(nativeQuery = true, value =  "SELECT DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-1) DAY), '%Y-%m-%d') as start,\n" +
            "       DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-7) DAY), '%Y-%m-%d') as end,\n" +
            "  sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY start, end\n" +
            "  ORDER BY start DESC, end DESC")

    public List<DoctorWeekInterface> findRecentWeekByDoctorId(
            @Param("doctorId") int doctorId);



    // 주간 매출 많은 순
    @Query(nativeQuery = true, value =  "SELECT DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-1) DAY), '%Y-%m-%d') as start,\n" +
            "       DATE_FORMAT(DATE_SUB(B.reservationDate, INTERVAL (DAYOFWEEK(B.reservationDate)-7) DAY), '%Y-%m-%d') as end,\n" +
            "  sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY start, end" +
            "  ORDER BY `SUM` DESC")

    public List<DoctorWeekInterface> findWeekSalesByDoctorId(
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




    // 일별 매출 최신순
    @Query(nativeQuery = true, value =  "SELECT DATE(B.reservationDate) AS `DATE`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `DATE`\n" +
            "  order by `DATE` desc")

    public List<DoctorDateInterface> findRecentDateByDoctorId(
            @Param("doctorId") int doctorId);



    // 일별 매출 많은 순
    @Query(nativeQuery = true, value =  "SELECT DATE(B.reservationDate) AS `DATE`, sum(A.cost) AS `SUM`, COUNT(DISTINCT B.id) AS `patientCount`\n" +
            "  FROM `reservation` AS B\n" +
            "  join `treatment_statement` AS A\n" +
            "  on B.id = A.reservationId\n" +
            "  join `patient` AS C\n" +
            "  on B.patientId = C.id\n" +
            "  where A.doctorId = :doctorId\n" +
            "  GROUP BY `DATE`" +
            "  ORDER BY `SUM` DESC")

    public List<DoctorDateInterface> findDateSalesByDoctorId(
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





    // 기간별 매출 최신순
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
                    "  GROUP BY `DATE`" +
                    "  order by `DATE` desc")

    public List<DoctorPeriodInterface> findRecentPeriodByDoctorIdAndStartDateAndEndDate(
            @Param("doctorId") int doctorId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);







    // 기간별 매출이 많은 순
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
                    "  GROUP BY `DATE`" +
                    "  ORDER BY `SUM` DESC")

    public List<DoctorPeriodInterface> findSalesPeriodByDoctorIdAndStartDateAndEndDate(
            @Param("doctorId") int doctorId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);




    // 예약아이디로 조회
    List<Treatment_statement> findByReservationId(int reservationId);


}
