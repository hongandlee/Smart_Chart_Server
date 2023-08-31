package com.smartChart.reservation;


import com.smartChart.doctor.service.DoctorService;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.reservation.dto.SearchRequest;
import com.smartChart.reservation.dto.SearchResponse;
import com.smartChart.reservation.repository.ReservationInterface;
import com.smartChart.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorReservationController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PatientService patientService;

    private final DoctorService doctorService;

    private final ReservationService reservationService;


    /**
     * 예약 관리
     * @param request
     * @param session
     * @return
     */
    @GetMapping("/reservation")
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
            List<ReservationInterface> reservation = reservationService.findReservationByReservationDateAndReservationTimeAndDoctorId(request.getReservationDate(), request.getReservationTime(), doctorId);
            response.setData(reservation);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




} // end