package com.smartChart.survey.repository;

import com.smartChart.survey.dto.DoctorSurveyInterface;
import com.smartChart.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {



    @Query(nativeQuery = true, value =  "select D.name, C.answer, A.questionId, B.question, A.yes, A.no,A.unawareness\n" +
            "from sample_question AS B\n" +
            "join sample_answer AS A\n" +
            "on A.questionId = B.id\n" +
            "join survey AS C\n" +
            "on C.questionId = B.id\n" +
            "join patient AS D\n" +
            "on D.id = C.patientId\n" +
            "where C.patientId = :patientId\n" +
            "order by B.id asc"
    )
    public List<DoctorSurveyInterface> findByPatientId(
            @Param("patientId") int patientId);






}