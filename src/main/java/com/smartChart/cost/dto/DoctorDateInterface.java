package com.smartChart.cost.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public interface DoctorDateInterface {
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date getDate();

    Integer getSum();
    Integer getPatientCount();
}
