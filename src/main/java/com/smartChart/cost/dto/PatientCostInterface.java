package com.smartChart.cost.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface PatientCostInterface {

    int getId();
    String getHospitalName();

    String getName();


    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();




    String getPatientPaymentStatus();


    // 추가
    Integer getSum();

}
