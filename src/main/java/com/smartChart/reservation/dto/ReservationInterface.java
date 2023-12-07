package com.smartChart.reservation.dto;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;

public interface ReservationInterface {


    int getId();

    String getName();

    int getPatientId();

    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();

    Time getReservationTime();

    int getPhoneNumber();


    String getReservationStatus();

    String getPaymentStatus();


}
