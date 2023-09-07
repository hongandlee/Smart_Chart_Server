package com.smartChart.cost.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DoctorIncomeDTO {

    private Object Month;

    private Object RecentMonth;

    private Object Year;

    private Object RecentYear;

    private Object Week;

    private Object RecentWeek;

    private Object Date;

    private Object RecentDate;
}
