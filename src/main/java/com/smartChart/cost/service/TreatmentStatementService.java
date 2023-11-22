package com.smartChart.cost.service;


import com.smartChart.cost.dto.*;
import com.smartChart.cost.entity.Cost;
import com.smartChart.cost.entity.Treatment_statement;
import com.smartChart.cost.repository.CostRepository;
import com.smartChart.cost.repository.TreatmentStatementRepository;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TreatmentStatementService {

    private final TreatmentStatementRepository treatmentStatementRepository;
    private final CostRepository costRepository;
    private final ReservationRepository reservationRepository;





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


    public List<PatientCostInterface> selectCostByPatientId(int patientId) {return treatmentStatementRepository.findByPatientId(patientId);}

    public List<PatientCostViewInterface> selectCostByReservationId(int reservationId) {return costRepository.findByReservationId(reservationId);}

    public List<PatientCostSumInterface> selectSumByReservationId(int reservationId) {

        return costRepository.findSumByReservationId(reservationId);}


    // sum조회
    public Integer selectByReservationId(int reservationId) {
        return costRepository.findSUMbyReservationId(reservationId);
    }


    // 월별조회
    public List<DoctorMonthInterface> selectIncomeByDoctorId(int doctorId) {
        return treatmentStatementRepository.findByDoctorId(doctorId);
    }


    // 최신 월별조회
    public List<DoctorMonthInterface> selectRecentIncomeByDoctorId(int doctorId) {
        return treatmentStatementRepository.findRecentByDoctorId(doctorId);
    }


    // 월별 성벌조회
    public List<DoctorGenderMonthInterface> selectGenderByDoctorId(int doctorId) {
        return treatmentStatementRepository.findGenderByDoctorId(doctorId);
    }

    // 월별 매출 많은 순 조회
    public List<DoctorMonthInterface> selectSalesByDoctorId(int doctorId) {
        return treatmentStatementRepository.findSalesByDoctorId(doctorId);
    }


    // 월별 매출 평균 나이 조회
    public List<DoctorAgeMonthInterface> selectAgeByDoctorId(int doctorId) {
        return treatmentStatementRepository.findAgeMonthByDoctorId(doctorId);
    }

    // 년별조회
    public List<DoctorYearInterface> selectYearByDoctorId(int doctorId) {
        return treatmentStatementRepository.findYearByDoctorId(doctorId);
    }


    // 최신 년별조회
    public List<DoctorYearInterface> selectRecentYearByDoctorId(int doctorId) {
        return treatmentStatementRepository.findRecentYearByDoctorId(doctorId);
    }

    // 년별 매출이 많은 순 조회
    public List<DoctorYearInterface> selectYearSalesByDoctorId(int doctorId) {
        return treatmentStatementRepository.findYearSalesByDoctorId(doctorId);
    }


    // 주간조회
    public List<DoctorWeekInterface> selectWeekByDoctorId(int doctorId) {
        return treatmentStatementRepository.findWeekByDoctorId(doctorId);
    }


    // 최신 주간조회
    public List<DoctorWeekInterface> selectRecentWeekByDoctorId(int doctorId) {
        return treatmentStatementRepository.findRecentWeekByDoctorId(doctorId);
    }


    // 주간 매출이 많은 순 조회
    public List<DoctorWeekInterface> selectWeekSalesByDoctorId(int doctorId) {
        return treatmentStatementRepository.findWeekSalesByDoctorId(doctorId);
    }

    // 일별조회
    public List<DoctorDateInterface> selectDateDoctorId(int doctorId) {
        return treatmentStatementRepository.findDateByDoctorId(doctorId);
    }


    // 최신 일별조회
    public List<DoctorDateInterface> selectRecentDateDoctorId(int doctorId) {
        return treatmentStatementRepository.findRecentDateByDoctorId(doctorId);
    }


    // 일별 매출 많은 순 조회
    public List<DoctorDateInterface> selectDateSalesDoctorId(int doctorId) {
        return treatmentStatementRepository.findDateSalesByDoctorId(doctorId);
    }

    // 기간별 조회
    public List<DoctorPeriodInterface> selectPeriodDoctorId(int doctorId, Date startDate, Date endDate) {
        return treatmentStatementRepository.findPeriodByDoctorIdAndStartDateAndEndDate(doctorId, startDate, endDate);
    }

    // 기간별 최신순
    public List<DoctorPeriodInterface> selectRecentPeriodDoctorId(int doctorId, Date startDate, Date endDate) {
        return treatmentStatementRepository.findRecentPeriodByDoctorIdAndStartDateAndEndDate(doctorId, startDate, endDate);
    }


    // 기간별 매출이 많은 순
    public List<DoctorPeriodInterface> selectSalesPeriodDoctorId(int doctorId, Date startDate, Date endDate) {
        return treatmentStatementRepository.findSalesPeriodByDoctorIdAndStartDateAndEndDate(doctorId, startDate, endDate);
    }


    public void deleteReservationById(int reservationId) {
        List<Treatment_statement> treatment_statement = treatmentStatementRepository.findByReservationId(reservationId);
        if (treatment_statement != null) {
            treatmentStatementRepository.deleteAll(treatment_statement);
        }

    }

}
