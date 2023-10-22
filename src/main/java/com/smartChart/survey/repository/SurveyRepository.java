package com.smartChart.survey.repository;

import com.smartChart.survey.entity.Survey;
import com.smartChart.survey.dto.DoctorSurveyInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Integer> {



    @Query(nativeQuery = true, value =  "select C.questionNumber, C.answer\n" +
            "from survey AS C\n" +
            "join patient AS D\n" +
            "on D.id = C.patientId\n" +
            "where C.patientId = :patientId\n" +
            "order by C.id asc"
    )
    public List<DoctorSurveyInterface> findByPatientId(
            @Param("patientId") int patientId);






}