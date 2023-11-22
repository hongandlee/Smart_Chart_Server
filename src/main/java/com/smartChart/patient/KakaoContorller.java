package com.smartChart.patient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartChart.Response.Message;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.dto.RequestDto.*;
import com.smartChart.patient.entity.Patient;
import com.smartChart.patient.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Controller
public class KakaoContorller {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${cos.key}")
    private String cosKey;

    @Autowired
    private PatientService patientService;


    /**
     * 카카오 로그인
     * @param request
     * @return
     */
    @PostMapping("/auth/kakao/callback")
    public ResponseEntity<Message> kakaoCallback(
            @RequestBody kakaoRequest request) {


        //HttpServletRequest request


        // POST방식으로 key=value 데이터를 요청(카카오쪽으로)
        // <3가지 요청방식>
        //Retrofit2
        //OKHttp
        //RestTemplate

        // 프론트엔드에서 코드를 받지 않고, 백엔드에서 직접 HttpServletRequest를 통해 코드 추출
        //String code = request.getParameter("code");



        String code = request.getCode();
        System.out.println(code);

        RestTemplate rt = new RestTemplate();


        //HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");
  
        // * 변수를 만들어서 하는게 더 좋음.
        // HttpBody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","338b152f34fe502634c3e709272cd726");
        params.add("redirect_uri","http://localhost:3000/auth/kakao/callback");
        params.add("code",code);

        // 위 headers와 params의 값을 갔고 있는 entity 생성
        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
                new HttpEntity<>(params,headers);

        // 실제 요청
        // Http 요청하기 - Post방식으로 - response 변수의 응답 받음. // 여기서 에러남...!!!!!
//        ResponseEntity<String> response = rt.exchange( // exchange()함수는 HttpEntity를 담게 되어있음 , 그래서 위에 HttpEntity를 만듬
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest, // header 값과 body 값이 들어있음
//                String.class // 응답은 String으로 받음
//        );



        

        try {
            ResponseEntity<String> response = rt.exchange(
                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

            // 여기에 정상적인 응답을 처리하는 코드 추가
            // ...

        } catch (HttpClientErrorException e) {
            // 여기에 예외 처리 코드 추가
            System.out.println("HTTP Status Code: " + e.getRawStatusCode());
            System.out.println("Response Body: " + e.getResponseBodyAsString());
        }






        // 라이브러리 종류 - Gson, Json Simple, ObjectMapper
        // Jason 데이터를 자바에서 처리하기 위해 Object를 바꾼 것.
        ObjectMapper objectMapper = new ObjectMapper();
        OAuthToken oAuthToken = null;

        try {
           oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("####################### 카카오 엑세스 토큰 : " + oAuthToken.getAccess_token());


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


        logger.info(response2.getBody());


        ObjectMapper objectMapper2 = new ObjectMapper();
        KakaoProfile kakaoProfile = null;

        try {
            kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // Patient 오브젝트 : name, email, password
        logger.info("####################### 카카오 아이디(번호) : " + kakaoProfile.getId());
        logger.info("####################### 카카오 이메일 : " + kakaoProfile.getKakao_account().getEmail());


       // System.out.println("환자 이름 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId()); //   // 예를 들어 유저네임 중복 안되게 하기 위한 tip
        logger.info("####################### 환자 이름 : " + kakaoProfile.getProperties().getNickname());
        logger.info("####################### 환자 이메일 :" + kakaoProfile.getKakao_account().getEmail());
        // UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
        //UUID garbagePassword = UUID.randomUUID();  // 임시방편으로 넣으 놓은 비밀번호임. (결국 쓰레기 비밀번호)
        logger.info("####################### 환자서버 패스워드 :" + cosKey);


        // dto 값 넣기
        PatientJoinRequest kakaoRequest = PatientJoinRequest.builder()
                        .email(kakaoProfile.getKakao_account().getEmail())
                        .password(cosKey)
                        .name(kakaoProfile.getProperties().getNickname())
                        .gender("null")
                        .age(0)
                        .phoneNumber(0)
                        .role(Role.PATIENT)
                        .oauth("kakao")
                        .build();

        PatientLoginRequest kakaoLoginRequest = PatientLoginRequest.builder()
                .email(kakaoProfile.getKakao_account().getEmail())
                .password(cosKey.toString())
                .build();


        // 가입자 혹은 비가입자 체크 해서 처리
        logger.info("#######################", kakaoRequest.getEmail());
        Patient originPatient = patientService.회원찾기(kakaoRequest.getEmail());


        // 비가입자일 경우 -> 회원가입
        if(originPatient.getEmail() == null) {
            logger.info("####################### 기존 회원이 아니기에 자동 회원가입을 진행합니다.");
            patientService.register(kakaoRequest);
        }

        logger.info("####################### 자동 로그인을 진행합니다.");
        // 가입자일 경우 -> 로그인
        patientService.authenticate(kakaoLoginRequest);



        // message
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("카카오톡 로그인 성공");


        return new ResponseEntity<>(message,  HttpStatus.OK);
    }
}
