package com.smartChart.treatment.service;


import com.smartChart.doctor.entity.Doctor;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationRepository;
import com.smartChart.treatment.dto.DoctorTreatmentInterface;
import com.smartChart.treatment.dto.TreatmentInterface;
import com.smartChart.treatment.entity.Treatment;
import com.smartChart.treatment.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentService {


    private final ReservationRepository reservationRepository;

    private final TreatmentRepository treatmentRepository;

    // select - 의사 진료관리 조회
    public List<DoctorTreatmentInterface> selectTreatmentByReservationId(int reservationId) {
        return reservationRepository.findByReservationId(reservationId);
    }




    // insert - 진료관리 저장
    public Treatment addTreatment (Reservation reservationId, Doctor doctorId, String medicalHistory,
                                   String mainSymptoms, String currentSymptoms, String treatmentPlan,
                                   String note) {

        Treatment treatment = treatmentRepository.save(
                Treatment.builder()
                        .reservation(reservationId)
                        .doctor(doctorId)
                        .medicalHistory(medicalHistory)
                        .mainSymptoms(mainSymptoms)
                        .currentSymptoms(currentSymptoms)
                        .treatmentPlan(treatmentPlan)
                        .note(note)
                        .build());
        return treatment;

    }


    // select - 진료 관리 의사 소견서 조회
    public List<TreatmentInterface> selectInfoByReservationId(int reservationId) { return treatmentRepository.finListByReservationId(reservationId);}
}
