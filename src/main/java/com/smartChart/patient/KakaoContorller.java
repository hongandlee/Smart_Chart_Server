package com.smartChart.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.dto.RequestDto.PatientJoinRequest;
import com.smartChart.patient.dto.RequestDto.PatientLoginRequest;
import com.smartChart.patient.entity.KakaoProfile;
import com.smartChart.patient.entity.OAuthToken;
import com.smartChart.patient.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Controller
public class KakaoContorller {


    @Autowired
    private PatientService patientService;

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallback(String code) { // data 리턴해주는 컨틀롤러 함수

        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        // <3가지 요청방식>
        //Retrofit2
        //OKHttp
        //RestTemplate


        RestTemplate rt = new RestTemplate();


        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        // * 변수를 만들어서 하는게 더 좋음.
        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","338b152f34fe502634c3e709272cd726");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);

        // 위 headers와 params의 값을 갔고 있는 entity 생성
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        // 실제 요청
        // Http 요청하기 - Post방식으로 - response 변수의 응답 받음.
        ResponseEntity<String> response = rt.exchange( // exchange()함수는 HttpEntity를 담게 되어있음 , 그래서 위에 HttpEntity를 만듬
                "https://kauth.kakao.com/oauth/token ",
                HttpMethod.POST,
                kakaoTokenRequest, // header 값과 body 값이 들어있음
                String.class // 응답은 String으로 받음

        );


        // 라이브러리 종류 - Gson, Json Simple, ObjectMapper
        // Jason 데이터를 자바에서 처리하기 위해 Object를 바꾼 것.
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
           oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println("카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());



        RestTemplate rt2 = new RestTemplate();


        //HttpHeader 오브젝트 생성
        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization","Bearer " + oAuthToken.getAccess_token());  // Bearer 뒤에 한칸 띄어놔야함.

        headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");


        // 위 headers와 params의 값을 갔고 있는 entity 생성
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 =
                new HttpEntity<>(headers2);

        // 실제 요청
        // Http 요청하기 - Post방식으로 - response 변수의 응답 받음.
        ResponseEntity<String> response2 = rt2.exchange( // exchange()함수는 HttpEntity를 담게 되어있음 , 그래서 위에 HttpEntity를 만듬
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest2, // header 값이 들어있음
                String.class // 응답은 String으로 받음

        );


        System.out.println(response2.getBody());


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Patient 오브젝트 : name, email, password
        System.out.println("카카오 아이디(번호) : " + kakaoProfile.getId());
        System.out.println("카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());


       // System.out.println("환자 이름 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId()); //   // 예를 들어 유저네임 중복 안되게 하기 위한 tip
        System.out.println("환자 이름 : " + kakaoProfile.getProperties().getNickname());
        System.out.println("환자 이메일 :" + kakaoProfile.getKakao_account().getEmail());
        UUID garbagePassword = UUID.randomUUID();  // 임시방편으로 넣으 놓은 비밀번호임. (결국 쓰레기 비밀번호)
        System.out.println("환자서버 패스워드 :" + garbagePassword);


        // dto 값 넣기
        PatientJoinRequest kakaoRequest = PatientJoinRequest.builder()
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .password(garbagePassword.toString())
                        .name(kakaoProfile.getProperties().getNickname())
                        .build();

        PatientLoginRequest kakaoLoginRequest = PatientLoginRequest.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .password(garbagePassword.toString())
                .build();
        System.out.println("111111111111111");

        // 가입자 혹은 비가입자 체크 해서 처리
        System.out.println(kakaoRequest.getEmail());
        Patient originPatient = patientService.회원찾기(kakaoRequest.getEmail());
        System.out.println("22222222222");

        // 비가입자일 경우 -> 회원가입
        if(originPatient.getEmail() == null) {
            System.out.println("기존 회원이 아닙니다.........");
            patientService.register(kakaoRequest);
        }
        System.out.println("333333333333");
        // 가입자일 경우 -> 로그인
        patientService.authenticate(kakaoLoginRequest);
        System.out.println("444444444444");

        return "회원가입 완료 및 로그인 처리 완료"; // "카카오 토큰 요청 완료: 토큰 요청에 대한 응답 :"
    }
}
