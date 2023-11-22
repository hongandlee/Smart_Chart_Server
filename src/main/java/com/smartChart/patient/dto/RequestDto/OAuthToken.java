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
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("refresh_token_expires_in")
    private int refreshTokenExpiresIn;

    // 추가: error 관련 필드
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;

    @JsonProperty("error_code")
    private String errorCode;

}
