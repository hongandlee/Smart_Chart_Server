package com.smartChart.treatment.service;


import com.smartChart.reservation.repository.ReservationRepository;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentService {


    private final ReservationRepository reservationRepository;

    // select - 의사 진료관리 조회
    public List<DoctorTreatmentInterface> selectTreatmentByReservationId(int reservationId) {
        return reservationRepository.findByReservationId(reservationId);
    }
}
