package com.smartChart.Response;


import lombok.Data;

@Data
public class Message {

    private Integer code;
    private String message;
    private Object data;

    public Message() {
        this.code = null;
        this.message = null;
        this.data = null;
    }
}
