package com.smartChart.survey.repository;


import com.smartChart.survey.entity.Sample_question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sample_question_Repository extends JpaRepository<Sample_question, Integer> {


    @Query(nativeQuery = true, value =  "select A.questionId, B.question, A.yes, A.no,A.unawareness\n" +
    "from sample_question AS B\n" +
    "join sample_answer AS A\n" +
    "on A.questionId = B.id")
    public List<SurveyInterface> findQuestionAndQuestionIdAndYesAndNoAndUnawareness();

    Sample_question findAllById(int id);

    List<Sample_question> findAllListById(int id);
}
