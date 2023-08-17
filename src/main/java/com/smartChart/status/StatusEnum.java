package com.smartChart.status;

public enum StatusEnum {
    ;

//    Success(200, "성공"),
//    Fail(500, "관리자에게 문의해주시기 바랍니다.");
//
//    int statusCode;
//    String code;
//
//    StatusEnum(int statusCode, String code) {
//        this.statusCode = statusCode;
//        this.code = code;
//    }

    int Success = 200;
    int Fail = 500;

    StatusEnum(int success, int fail) {
        Success = success;
        Fail = fail;
    }
}
