package com.smartChart.cost.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


public interface DoctorWeekInterface {
    @JsonFormat(pattern = "yyyy-MM-dd")
    String getStart();
    @JsonFormat(pattern = "yyyy-MM-dd")
    String getEnd();
    Integer getSum();
    Integer getPatientCount();
}
