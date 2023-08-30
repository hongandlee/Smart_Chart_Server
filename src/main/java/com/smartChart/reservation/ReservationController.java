package com.smartChart.reservation;

import com.smartChart.Response.Message;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.entity.Patient;
import com.smartChart.reservation.dto.ReservationRequest;
import com.smartChart.reservation.dto.ReservationResponse;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;


@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class ReservationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PatientService patientService;

    private final DoctorService doctorService;

    private final ReservationService reservationService;





    @GetMapping("/reservation-view/{doctorId}")
    public ResponseEntity<ReservationResponse> reservation_view(
            @PathVariable int doctorId,
            HttpSession session){

        // session
        Integer patientId = (Integer)session.getAttribute("patientId");

        // db
        Patient patient = patientService.findPatientById(patientId);
        Doctor doctor = doctorService.findById(doctorId);

        ReservationResponse response = new ReservationResponse();
        response.setPatientName(patient.getName());
        response.setPatiengGender(patient.getGender());
        response.setPatientgAge(patient.getAge());
        response.setPatiengPhoneNumber(patient.getPhoneNumber());
        response.setHospitalName(doctor.getHospitalName());
        response.setDoctorId(doctorId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }








    /**
     * 병원 예약 가능 조회
     */
    @RequestMapping("/check-reservation")
    public ResponseEntity<Message> check_reservation (
            @RequestBody ReservationRequest request
            ) {

        // db
        Reservation reservation = reservationService.findByReservationDateAndReservationTimeAndDoctorId(request.getReservationDate(), request.getReservationTime(), request.getDoctorId());

        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        if(reservation == null) {        // 예약 가능
            message.setCode(200);
            message.setMessage("예약이 가능합니다.");

        } else {                     // 예약 불가능
            message.setCode(409);
            message.setMessage("예약 마감입니다.");
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }





    /**
     * 병원 예약학기
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/reservation")
    public ResponseEntity<Message> reservation (
            @RequestBody ReservationRequest request,
            HttpSession session
    ) {
        // session
        int patientId = (int)session.getAttribute("patientId");
        Patient patient = patientService.findPatientById(patientId);

        // db
        Doctor doctor = doctorService.findById(request.getDoctorId());

        Reservation check_reservation = reservationService.findByReservationDateAndReservationTimeAndDoctorId(request.getReservationDate(), request.getReservationTime(), request.getDoctorId());


        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        if (check_reservation == null) {
            // db
            Reservation reservation = reservationService.addReservation(patient, doctor, request.getReservationDate(),request.getReservationTime());

            message.setCode(200);
            message.setMessage("예약 되었습니다.");
        } else {
            message.setCode(409);
            message.setMessage("예약 날짜와 시간을 다시 설정해주세요.");
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }




}
