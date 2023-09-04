package com.smartChart.cost;


import com.smartChart.cost.dto.PatientCostInterface;
import com.smartChart.cost.service.TreatmentStatementService;
import com.smartChart.reservation.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientCostController {


    private final TreatmentStatementService treatmentStatementService;


    @GetMapping("/cost-view")
    public ResponseEntity<SearchResponse> treatment_view(
            HttpSession session) {

        // session
        Integer patientId = (Integer) session.getAttribute("patientId");

        // db
        List<PatientCostInterface> patientCostInterfaceList = treatmentStatementService.selectCostByPatientId(patientId);


        SearchResponse response = new SearchResponse();
        response.setData(patientCostInterfaceList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
