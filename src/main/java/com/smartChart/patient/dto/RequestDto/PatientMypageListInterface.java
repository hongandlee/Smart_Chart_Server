package com.smartChart.patient.dto.RequestDto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;

public interface PatientMypageListInterface {

    String getHospitalName();


    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();

    Time getReservationTime();


    String getReservationStatus();
}
