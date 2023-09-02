package com.smartChart.survey;


import com.smartChart.Response.DataResponse;
import com.smartChart.Response.Message;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.entity.Patient;
import com.smartChart.survey.dto.AnswerRequest;
import com.smartChart.survey.dto.QuestionRequest;
import com.smartChart.survey.dto.SurveyDTO;
import com.smartChart.survey.entity.Sample_answer;
import com.smartChart.survey.entity.Sample_question;
import com.smartChart.survey.entity.Survey;
import com.smartChart.survey.repository.SurveyInterface;
import com.smartChart.survey.repository.SurveyRepository;
import com.smartChart.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class SurveyController {


    private final SurveyService surveyService;

    private final PatientService patientService;

    private final SurveyRepository surveyRepository;

    /**
     * 기본 건강체크 샘플 질문 만들기
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




    /**
     * 기본 건강체크 샘플 답변 만들기
     * @param request
     * @return
     */
    @PostMapping("/answer")
    public ResponseEntity<DataResponse> answer(
            @RequestBody AnswerRequest request) {

        // db
        Sample_question sampleQuestion = surveyService.selectSampleQuestionById(request.getQuestionId());
        Sample_answer sampleAnswer = surveyService.addAnswer(sampleQuestion, request.getYes(), request.getNo(),request.getUnawareness());

        DataResponse response = new DataResponse();
        response.setData(sampleAnswer);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    
    /**
     * 기본 건강체크 Survey
     * @param surveyDTOList
     * @param session
     * @return
     */
    @Transactional
    @PostMapping("/health-check")
    public ResponseEntity<Message> health_check(
            @RequestBody List<SurveyDTO> surveyDTOList,
            HttpSession session) {

        // session
        Integer patientId = (Integer)session.getAttribute("patientId");
        Patient patient = patientService.findPatientById(patientId);


        List<Survey> surveyToSave = new ArrayList<>();

        for(SurveyDTO surveyDTO : surveyDTOList) {
            Survey survey = new Survey();
            Sample_question sampleQuestion = surveyService.selectSampleQuestionById(surveyDTO.getQuestionId());
            survey.setPatient(patient);
            survey.setSample_question(sampleQuestion);
            survey.setAnswer(surveyDTO.getAnswer());
            surveyToSave.add(survey);
        }
        surveyRepository.saveAll(surveyToSave);


        // message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("성공");

        return new ResponseEntity<>(message, headers, HttpStatus.OK); // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함

    }









    /**
     * 기본 건강체크 화면
     * @return
     */
    @GetMapping("/health-check-view")
    public ResponseEntity<DataResponse> healCheckView () {

        // db
       List<SurveyInterface> questions = surveyService.selectQuestions();

        DataResponse response = new DataResponse();
        response.setData(questions);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





}
