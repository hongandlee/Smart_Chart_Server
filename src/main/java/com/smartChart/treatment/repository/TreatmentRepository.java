package com.smartChart.treatment.repository;

import com.smartChart.treatment.dto.TreatmentInterface;
import com.smartChart.treatment.entity.Treatment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Integer> {



    // 진료 관리 의사 의견서 조회
    @Query(nativeQuery = true, value =  "select B.currentSymptoms, B.mainSymptoms, B.medicalHistory, B.note, B.treatmentPlan\n" +
            "from treatment AS B\n" +
            "join reservation AS A\n" +
            "on B.reservationId = A.id\n" +
            "where B.id = :reservationId"
    )
    public List<TreatmentInterface> finListByReservationId(
            @Param("reservationId") int reservationId);

}
