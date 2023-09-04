package com.smartChart.treatment;


import com.smartChart.Response.Message;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.service.ReservationService;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import com.smartChart.treatment.dto.TreatmentRequest;
import com.smartChart.treatment.dto.TreatmentResponse;
import com.smartChart.treatment.entity.Treatment;
import com.smartChart.treatment.service.TreatmentService;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class TreatmentController {

    private final TreatmentService treatmentService;

    private final DoctorService doctorService;

    private final ReservationService reservationService;


    /**
     * 진료비 관리 화면
     * @param reservationId
     * @param session
     * @return
     */
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


    /**
     * 진료 관리 저장
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @PostMapping("/treatment")
    public ResponseEntity<Message> treatment(
            @RequestBody TreatmentRequest request,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        Doctor doctor = doctorService.findById(doctorId);

        // db
        Reservation reservation = reservationService.findById(request.getReservationId());
        Treatment treatment = treatmentService.addTreatment(reservation, doctor, request.getMedicalHistory(),
                request.getMainSymptoms(), request.getCurrentSymptoms(), request.getTreatmentPlan(),
                request.getNote());

        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("저장되었습니다.");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}