package com.smartChart.reservation;


import com.smartChart.Response.DataResponse;
import com.smartChart.Response.Message;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.reservation.dto.*;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationRepository;
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
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorReservationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PatientService patientService;

    private final DoctorService doctorService;

    private final ReservationService reservationService;

    private final ReservationRepository reservationRepository;




    public static final String CURRENT_DATE = LocalDate.now().toString();

    /**
     * 예약 관리
     * @param request
     * @param session
     * @return
     */
    @PostMapping("/reservation")
    public ResponseEntity<SearchResponse> reservationCheck(
            @RequestBody SearchRequest request,
            HttpSession session) {


        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        SearchResponse response = new SearchResponse();
        if (request.getReservationTime() == null) {
            // db
            List<ReservationInterface> reservationList = reservationService.findAllByReservationDateAndReservationTimeAndDoctorId(request.getReservationDate(), doctorId);
            response.setData(reservationList);
        } else {
            // db
            List<ReservationInterface> reservation = reservationService.findReservationByReservationDateAndReservationTimeAndDoctorId(request.getReservationDate(), request.getReservationTime(), doctorId);
            response.setData(reservation);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 예약 관리 화면 (최근 예약 건수 10개)
     * @param session
     * @return
     */
    @GetMapping("/reservation-view")
    public ResponseEntity<SearchResponse> reservation_view(
            HttpSession session) {


        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        SearchResponse response = new SearchResponse();

        // db
        List<ReservationInterface> reservationList = reservationService.findByDoctorIdOrderByIdDesc(doctorId);
        response.setData(reservationList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 의사 환자 대기 관리
     * @param session
     * @return
     */
    @GetMapping("/waiting-list-view")
    public ResponseEntity<DataResponse> waiting_list_view(
            HttpSession session) {


        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");


        // db
        List<WaitingInterface> reservationList = reservationService.findRecentReservations(doctorId);
        DataResponse response = new DataResponse();
        response.setData(reservationList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 의사 환자 대기 관리 데이터 변경
     * @param request
     * @return
     */
    @PatchMapping("/waiting-list-status")
    public ResponseEntity<Message> waiting_list_status(
            @RequestBody WaitingRequest request,
            HttpSession session
    ){
        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        //db
        Reservation reservation = reservationService.findById(request.getReservationId());
        reservation.setWaitingStatus(request.getWaitingStatus());
        if(request.getWaitingStatus() != null) {
            reservation = reservation.toBuilder()
                    .waitingStatus(request.getWaitingStatus())
                    .build();
            reservation = reservationRepository.save(reservation);
        }

        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("대기 상태가 변경되었습니다.");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }


} // end