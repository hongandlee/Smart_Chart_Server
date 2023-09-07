package com.smartChart.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;

public interface WaitingInterface {

    Integer getId();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();

    Time getReservationTime();

    String getPatientName();

}
