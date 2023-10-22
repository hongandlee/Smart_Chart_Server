package com.smartChart.reservation;


import com.smartChart.Response.DataResponse;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.reservation.dto.ReservationInterface;
import com.smartChart.reservation.dto.SearchRequest;
import com.smartChart.reservation.dto.SearchResponse;
import com.smartChart.reservation.dto.WaitingInterface;
import com.smartChart.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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


} // end