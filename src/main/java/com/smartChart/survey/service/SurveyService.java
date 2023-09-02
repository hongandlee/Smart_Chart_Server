package com.smartChart.survey.service;


import com.smartChart.survey.entity.Sample_answer;
import com.smartChart.survey.entity.Sample_question;
import com.smartChart.survey.repository.Sample_answer_Repository;
import com.smartChart.survey.repository.Sample_question_Repository;
import com.smartChart.survey.repository.SurveyInterface;
import com.smartChart.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor  // 새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
public class SurveyService {

    private final Sample_question_Repository questionRepository;


    private final Sample_answer_Repository answerRepository;


    private final SurveyRepository surveyRepository;




    /**
     * 설문지 질문 만들기
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




    /**
     * 샘플 답지 만들기
     * @param sample_question
     * @param yes
     * @param no
     * @param unawareness
     * @return
     */
    public Sample_answer addAnswer(Sample_question sample_question, String yes, String no, String unawareness) {

        Sample_answer answer = answerRepository.save(
                Sample_answer.builder()
                        .sample_question(sample_question)
                        .yes(yes)
                        .no(no)
                        .unawareness(unawareness)
                        .build());
        return answer;
    }





    public List<SurveyInterface> selectQuestions () {
        return questionRepository.findQuestionAndQuestionIdAndYesAndNoAndUnawareness();
    }




    // select - SampleQuestion
    public Sample_question selectSampleQuestionById (int questionId){
        return questionRepository.findAllById(questionId);
    }

    public List<Sample_question> selectListSampleQuestionById(int questionId) {
        return questionRepository.findAllListById(questionId);
    }



    public List<Sample_answer> selectListAnswerById(int answerId) {
        return answerRepository.findListById(answerId);
    }

    // select
    public Sample_answer selectAnswerById(int answerId) {
        return answerRepository.findAllById(answerId);
    }


}
