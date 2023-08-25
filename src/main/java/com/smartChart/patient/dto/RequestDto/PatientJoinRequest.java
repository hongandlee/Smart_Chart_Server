package com.smartChart.patient.dto.RequestDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// * 회원가입
@Data // @Getter / @Setter, @ToString, @EqualsAndHashCode와 @RequiredArgsConstructor, @Value 를 합쳐놓은 종합 선물 세트
@Builder  // 객체를 생성할 수 있는 빌더를 builder() 함수를 통해 얻고 거기에 셋팅하고자 하는 값을 셋팅하고 마지막에 build()를 통해 빌더를 작동 시켜 객체를 생성
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자를 만듦
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
public class PatientJoinRequest {


    private String email;


    private String password;


    private String name;


    private String gender;


    private int age;


    private int phoneNumber;

    private String oauth; // kakao, google
}
