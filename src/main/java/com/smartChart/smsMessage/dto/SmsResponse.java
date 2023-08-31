package com.smartChart.smsMessage.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class SmsResponse {


    private String statusCode;
    private String statusName;
    private String reservationStatus;
}
