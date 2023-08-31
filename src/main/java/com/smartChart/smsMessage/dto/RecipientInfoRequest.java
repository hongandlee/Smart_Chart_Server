package com.smartChart.smsMessage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class RecipientInfoRequest {

    private int reservationId;
    private String recipientPhoneNumber;
    private String content;
}
