package com.smartChart.survey.repository;


import com.smartChart.survey.entity.Sample_answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Sample_answer_Repository extends JpaRepository<Sample_answer, Integer> {



    Sample_answer findAllById(int id);

    List<Sample_answer> findListById(int id);
}
