package com.smartChart.survey;

import com.smartChart.Response.DataResponse;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.survey.dto.DoctorSurveyInterface;
import com.smartChart.survey.dto.DoctorSurveyRequest;
import com.smartChart.survey.service.SurveyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorSurveyController {


    private final SurveyService surveyService;

    private final PatientService patientService;

    @GetMapping("/health-check")
    public ResponseEntity<DataResponse> healCheckView (
            @RequestBody DoctorSurveyRequest request) {

        // db
        List<DoctorSurveyInterface> survey = surveyService.selectSurvey(request.getPatientId());

        DataResponse response = new DataResponse();
        response.setData(survey);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
