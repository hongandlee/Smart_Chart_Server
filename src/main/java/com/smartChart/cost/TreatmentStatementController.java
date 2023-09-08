package com.smartChart.cost;


import com.smartChart.Response.Message;
import com.smartChart.cost.dto.*;
import com.smartChart.cost.entity.Cost;
import com.smartChart.cost.entity.Treatment_statement;
import com.smartChart.cost.repository.CostRepository;
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
    private final CostRepository costRepository;


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
    public ResponseEntity<Message> treatmentStatement (
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
            treatmentStatement.setTreatment(treatmentStatementDTO.getTreatment());
            treatmentStatement.setCost(treatmentStatementDTO.getCost());
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
     * @param requestList
     * @param session
     * @return
     */
    @Transactional
    @PostMapping("/cost")
    public ResponseEntity<Message> cost (
            @RequestBody List<CostRequest> requestList,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");
        Doctor doctor = doctorService.findById(doctorId);


        List<Cost> costToSave = new ArrayList<>();

        for(CostRequest costRequest : requestList) {
            Cost cost = new Cost();
            cost.setDoctor(doctor);
            cost.setTreatment(costRequest.getTreatment());
            cost.setCost(costRequest.getCost());
            costToSave.add(cost);
        }
        costRepository.saveAll(costToSave);


        // message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("저장되었습니다.");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }





    // 매출 조회
    @GetMapping("/month-sales-view")
    public ResponseEntity<DoctorIncomeDTO> sales_view (HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        // db
        List<DoctorMonthInterface> monthIncome = treatmentStatementService.selectIncomeByDoctorId(doctorId);
        List<DoctorMonthInterface> monthRecentIncome = treatmentStatementService.selectRecentIncomeByDoctorId(doctorId);
        List<DoctorGenderMonthInterface> monthGenderIncome = treatmentStatementService.selectGenderByDoctorId(doctorId);
        List<DoctorMonthInterface> monthSales = treatmentStatementService.selectSalesByDoctorId(doctorId);
        List<DoctorAgeMonthInterface> monthAge = treatmentStatementService.selectAgeByDoctorId(doctorId);
        List<DoctorYearInterface> yearIncome = treatmentStatementService.selectYearByDoctorId(doctorId);
        List<DoctorYearInterface> yearRecentIncome = treatmentStatementService.selectRecentYearByDoctorId(doctorId);
        List<DoctorYearInterface> yearSales = treatmentStatementService.selectYearSalesByDoctorId(doctorId);
        List<DoctorWeekInterface> weekIncome = treatmentStatementService.selectWeekByDoctorId(doctorId);
        List<DoctorWeekInterface> weekRecentIncome = treatmentStatementService.selectRecentWeekByDoctorId(doctorId);
        List<DoctorWeekInterface> weekSales = treatmentStatementService.selectWeekSalesByDoctorId(doctorId);
        List<DoctorDateInterface> dateIncome = treatmentStatementService.selectDateDoctorId(doctorId);
        List<DoctorDateInterface> dateRecentIncome = treatmentStatementService.selectRecentDateDoctorId(doctorId);
        List<DoctorDateInterface> dateSales = treatmentStatementService.selectDateSalesDoctorId(doctorId);


        DoctorIncomeDTO response = new DoctorIncomeDTO();
        response.setMonth(monthIncome);
        response.setRecentMonth(monthRecentIncome);
        response.setGenderMonth(monthGenderIncome);
        response.setSalesMonth(monthSales);
        response.setAverageAgeMonth(monthAge);
        response.setYear(yearIncome);
        response.setRecentYear(yearRecentIncome);
        response.setSalesYear(yearSales);
        response.setWeek(weekIncome);
        response.setRecentWeek(weekRecentIncome);
        response.setSalesWeek(weekSales);
        response.setDate(dateIncome);
        response.setRecentDate(dateRecentIncome);
        response.setSalesDate(dateSales);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    // 기간별 조회
    @GetMapping("/period-sales")
    public ResponseEntity<DoctorPeriodResponse> period_sales (
            @RequestBody DoctorPeriodRequest periodDTO,
            HttpSession session) {

        // session
        Integer doctorId = (Integer) session.getAttribute("doctorId");

        List<DoctorPeriodInterface> periodIncome = treatmentStatementService.selectPeriodDoctorId(doctorId, periodDTO.getStartDate(),periodDTO.getEndDate());
        List<DoctorPeriodInterface> periodRecentIncome = treatmentStatementService.selectRecentPeriodDoctorId(doctorId, periodDTO.getStartDate(),periodDTO.getEndDate());
        List<DoctorPeriodInterface> periodSales = treatmentStatementService.selectSalesPeriodDoctorId(doctorId, periodDTO.getStartDate(),periodDTO.getEndDate());


        DoctorPeriodResponse response = new DoctorPeriodResponse();
        response.setPeriod(periodIncome);
        response.setRecent(periodRecentIncome);
        response.setSales(periodSales);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

} // end
