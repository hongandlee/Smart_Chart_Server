package com.smartChart.cost.repository;

import com.smartChart.cost.dto.DoctorCostInterface;
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






}
