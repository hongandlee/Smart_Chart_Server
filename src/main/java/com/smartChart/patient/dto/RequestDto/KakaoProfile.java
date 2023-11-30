package com.smartChart.patient.dto.RequestDto;

import lombok.Data;

@Data
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public Kakao_account kakao_account;



    @Data
    public class Properties {
        public String profile_nickname;

    }


    @Data
    public class Kakao_account {
        public Boolean profile_nickname_needs_agreement;
        public Boolean has_email;
        public Boolean email_needs_agreement;
        public Boolean is_email_valid;
        public Boolean is_email_verified;
        public String email;


    }

}













