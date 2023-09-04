package com.smartChart.cost;


import com.smartChart.Response.Message;
import com.smartChart.cost.dto.CostRequest;
import com.smartChart.cost.dto.DoctorCostInterface;
import com.smartChart.cost.dto.TreatmentStatementDTO;
import com.smartChart.cost.entity.Cost;
import com.smartChart.cost.entity.Treatment_statement;
import com.smartChart.cost.repository.TreatmentStatementRepository;
import com.smartChart.cost.service.TreatmentStatementService;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.service.ReservationService;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import com.smartChart.treatment.dto.TreatmentResponse;
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
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class TreatmentStatementController {


    private final TreatmentService treatmentService;
    private final DoctorService doctorService;
    private final ReservationService reservationService;
    private final TreatmentStatementService treatmentStatementService;
    private final TreatmentStatementRepository treatmentStatementRepository;



    /**
     * 진료비 청구 화면 조회
     * @param reservationId
     * @param session
     * @return
     */
    @GetMapping("/cost-view/{reservationId}")
    public ResponseEntity<TreatmentResponse> treatment_view(
            @PathVariable int reservationId,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        // db
        List<DoctorTreatmentInterface> treatment = treatmentService.selectTreatmentByReservationId(reservationId);
        List<DoctorCostInterface> costList = treatmentStatementService.selectCostList(doctorId);


        TreatmentResponse response = new TreatmentResponse();
        response.setData(treatment);
        response.setData2(costList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 진료비 청구
     * @param treatmentStatementDTOList
     * @param session
     * @return
     */
    @PostMapping("/treatment_statement")
    public ResponseEntity<Message> cost (
            @RequestBody List<TreatmentStatementDTO> treatmentStatementDTOList,
            HttpSession session){

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        Doctor doctor = doctorService.findById(doctorId);


        // db
        List<Treatment_statement> treatmentStatementsToSave = new ArrayList<>();
        for(TreatmentStatementDTO treatmentStatementDTO : treatmentStatementDTOList) {
            Treatment_statement treatmentStatement = new Treatment_statement();

            Reservation reservation = reservationService.findById(treatmentStatementDTO.getReservationId());
            treatmentStatement.setReservation(reservation);
            treatmentStatement.setDoctor(doctor);
            Cost cost = treatmentStatementService.selectCostById(treatmentStatementDTO.getCostId());
            treatmentStatement.setCost(cost);
            treatmentStatementsToSave.add(treatmentStatement);
        }
        treatmentStatementRepository.saveAll(treatmentStatementsToSave);


        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("저장되었습니다.");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }





    /**
     * 기본 치료비 책정
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @PostMapping("/cost")
    public ResponseEntity<Message> cost (
            @RequestBody CostRequest request,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        Doctor doctor = doctorService.findById(doctorId);


        Cost cost = treatmentStatementService.addCost(doctor, request.getTreatment(), request.getCost());

        // message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("저장되었습니다.");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }


} // end
