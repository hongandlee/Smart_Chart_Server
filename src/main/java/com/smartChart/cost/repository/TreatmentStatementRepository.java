package com.smartChart.cost.repository;

import com.smartChart.cost.entity.Treatment_statement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentStatementRepository extends JpaRepository<Treatment_statement, Integer> {




}
