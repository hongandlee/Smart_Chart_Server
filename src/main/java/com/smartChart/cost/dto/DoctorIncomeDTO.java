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

    private Object Year;

    private Object Week;

    private Object Date;
}
