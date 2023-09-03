package com.smartChart.treatment;


import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import com.smartChart.treatment.dto.TreatmentResponse;
import com.smartChart.treatment.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;


    @GetMapping("/treatment-view/{reservationId}")
    public ResponseEntity<TreatmentResponse> treatment_view(
            @PathVariable int reservationId,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        // db
        List<DoctorTreatmentInterface> treatment = treatmentService.selectTreatmentByReservationId(reservationId);



        TreatmentResponse response = new TreatmentResponse();
        response.setData(treatment);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}