package com.smartChart.treatment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface DoctorTreatmentInterface {

    int getId();

    String getHospitalName();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();

    String getName();

    Integer getPhoneNumber();

    String getGender();

    Integer getAge();

//    Integer getCost();
//
//    String getTreatment();
//
//    Integer getSum();
}
