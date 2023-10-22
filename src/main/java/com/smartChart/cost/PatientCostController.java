package com.smartChart.cost;


import com.smartChart.cost.dto.*;
import com.smartChart.cost.service.TreatmentStatementService;
import com.smartChart.reservation.dto.SearchResponse;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import com.smartChart.treatment.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientCostController {


    private final TreatmentStatementService treatmentStatementService;
    private final TreatmentService treatmentService;




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


    /**
     * 환자 진료비 보기
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/cost")
    public ResponseEntity<PatientCostResponse> cost (
            @RequestBody PatientCostRequest request,
            HttpSession session) {

        // session
        Integer patientId = (Integer) session.getAttribute("patientId");

        // db
        List<DoctorTreatmentInterface> treatment = treatmentService.selectTreatmentByReservationId(request.getReservationId());
        List<PatientCostViewInterface> cost = treatmentStatementService.selectCostByReservationId(request.getReservationId());
        List<PatientCostSumInterface> sum = treatmentStatementService.selectSumByReservationId(request.getReservationId());

        PatientCostResponse patientCostResponse = new PatientCostResponse();
        patientCostResponse.setData(treatment);
        patientCostResponse.setData2(cost);
        patientCostResponse.setDate3(sum);

        return new ResponseEntity<>(patientCostResponse, HttpStatus.OK);
    }
}
