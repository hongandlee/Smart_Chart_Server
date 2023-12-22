package com.smartChart.cost.repository;

import com.smartChart.cost.dto.DoctorCostInterface;
import com.smartChart.cost.dto.PatientCostInterface;
import com.smartChart.cost.dto.PatientCostSumInterface;
import com.smartChart.cost.dto.PatientCostViewInterface;
import com.smartChart.cost.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository extends JpaRepository<Cost, Integer> {

    @Query(nativeQuery = true, value =  "select B.id, B.treatment, B.cost\n" +
            "from cost AS B\n" +
            "join doctor AS A\n" +
            "on B.doctorId = A.id\n" +
            "where A.id = :doctorId"
    )
    public List<DoctorCostInterface> findByDoctorId(
            @Param("doctorId") int doctorId);



    Cost findById(int costId);



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




    @Query(nativeQuery = true, value =
            "SELECT A.treatment, A.cost\n" +
                    "FROM reservation AS B\n" +
                    "JOIN treatment_statement AS A\n" +
                    "ON A.reservationId = B.id\n" +
                    "WHERE A.reservationId = :reservationId\n"

    )
    public List<PatientCostViewInterface> findByReservationId(
            @Param("reservationId") int reservationId);



    @Query(nativeQuery = true, value =
            "SELECT sum(B.cost) as sum\n" +
                    "FROM treatment_statement AS B\n" +
                    "WHERE B.reservationId = :reservationId\n"
    )
    public List<PatientCostSumInterface> findSumByReservationId(
            @Param("reservationId") int reservationId);



    @Query(nativeQuery = true, value =
            "SELECT sum(B.cost) as sum\n" +
                    "FROM treatment_statement AS B\n" +
                    "WHERE B.reservationId = :reservationId\n" +
                    "GROUP BY B.reservationId"
    )
    public Integer findSUMbyReservationId(
            @Param("reservationId") int reservationId);
}
