package com.smartChart.reservation.repository;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;

public interface ReservationInterface {


    int getId();

    String getName();


    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getReservationDate();

    Time getReservationTime();

    int getPhoneNumber();


}
