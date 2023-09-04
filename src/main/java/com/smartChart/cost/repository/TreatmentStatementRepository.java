package com.smartChart.cost.repository;

import com.smartChart.cost.dto.PatientCostInterface;
import com.smartChart.cost.entity.Treatment_statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

}
