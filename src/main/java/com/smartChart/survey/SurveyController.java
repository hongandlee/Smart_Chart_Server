package com.smartChart.survey;


import com.smartChart.Response.DataResponse;
import com.smartChart.survey.dto.QuestionRequest;
import com.smartChart.survey.entity.Sample_question;
import com.smartChart.survey.repository.SurveyInterface;
import com.smartChart.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class SurveyController {


    private final SurveyService surveyService;


    /**
     * 설문지 질문 만들기
     * @param request
     * @return
     */
    @PostMapping("/question")
    public ResponseEntity<DataResponse> question (
            @RequestBody QuestionRequest request) {
        // db
        Sample_question sampleQuestion = surveyService.addQuestion(request.getQuestion());

        DataResponse response = new DataResponse();
        response.setData(sampleQuestion);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/health-check-view")
    public ResponseEntity<DataResponse> healCheckView () {

        // db
       List<SurveyInterface> questions = surveyService.selectQuestions();

        DataResponse response = new DataResponse();
        response.setData(questions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
