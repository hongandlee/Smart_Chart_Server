package com.smartChart.survey.repository;


import com.smartChart.survey.entity.Sample_question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sample_question_Repository extends JpaRepository<Sample_question, Integer> {


    @Query(nativeQuery = true, value =  "select id, question from sample_question")
    public List<SurveyInterface> findIdAndQustion();
}
