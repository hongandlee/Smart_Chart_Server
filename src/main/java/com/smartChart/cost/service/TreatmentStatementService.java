package com.smartChart.cost.service;


import com.smartChart.cost.dto.DoctorCostInterface;
import com.smartChart.cost.entity.Cost;
import com.smartChart.cost.repository.CostRepository;
import com.smartChart.cost.repository.TreatmentStatementRepository;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentStatementService {

    private final TreatmentStatementRepository treatmentStatementRepository;
    private final CostRepository costRepository;
    private final ReservationRepository reservationRepository;

    // insert
//    public Treatment_statement addCost (Reservation reservationId, Doctor doctorId, Date date, String treatment,
//                                        int cost) {
//
//        Treatment_statement treatmentCost = costRepository.save(
//                Treatment_statement.builder()
//                        .reservation(reservationId)
//                        .doctor(doctorId)
//                        .treatment(treatment)
//                        .cost(cost)
//                        .build());
//
//        return treatmentCost;
//
//    }



    public Cost addCost (Doctor doctorId, String treatment, int cost) {

        Cost treatmentCost = costRepository.save(
                     Cost.builder()
                        .doctor(doctorId)
                        .treatment(treatment)
                        .cost(cost)
                        .build());

        return treatmentCost;

    }




    // 진료치 청구 화면 조회 (치료내역서, 가격)
    public List<DoctorCostInterface> selectCostList(int doctorId) {
        return costRepository.findByDoctorId(doctorId);
    }


    public Cost selectCostById(int costId){ return costRepository.findById(costId);}
}
