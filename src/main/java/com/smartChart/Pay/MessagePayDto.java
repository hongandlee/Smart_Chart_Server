package com.smartChart.Pay;

import lombok.Data;

@Data
public class MessagePayDto {

    private Integer code;
    private String message;


    public MessagePayDto() {
        this.code = null;
        this.message = null;}
}
