package com.smartChart.survey.repository;


import com.smartChart.survey.entity.Sample_answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Sample_answer_Repository extends JpaRepository<Sample_answer, Integer> {


}
