package com.smartChart.patient.dto.RequestDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OAuthToken {

//    private String access_token;
//    private String token_type;
//    private String refresh_token;
//    private int expires_in;
//    private String  scope;
//    private int  refresh_token_expires_in;


    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("expires_in")
    private int expires_in;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token_expires_in")
    private int refresh_token_expires_in;

    // 추가: error 관련 필드
    private String error;

    @JsonProperty("error_description")
    private String error_description;

    @JsonProperty("error_code")
    private String error_code;

    // 추가: 에러 처리 메서드
    public boolean hasError() {
        return error != null;
    }

}
