package com.smartChart.survey.service;


import com.smartChart.survey.entity.Sample_question;
import com.smartChart.survey.repository.Sample_question_Repository;
import com.smartChart.survey.repository.SurveyInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor  // 새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
public class SurveyService {

    private final Sample_question_Repository questionRepository;




    /**
     * 설문지 질문
     * @param questions
     * @return
     */
    public Sample_question addQuestion (String questions) {

        Sample_question question = questionRepository.save(
                Sample_question.builder()
                        .question(questions)
                        .build());
        return question;
    }



    public List<SurveyInterface> selectQuestions () {
        return questionRepository.findIdAndQustion();
    }



}
