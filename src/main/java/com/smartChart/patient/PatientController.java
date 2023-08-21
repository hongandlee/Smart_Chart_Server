package com.smartChart.patient;


import com.smartChart.Response.Message;
import com.smartChart.auth.AuthenticationResponse;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.dto.PatientJoinRequest;
import com.smartChart.patient.dto.PatientLoginRequest;
import com.smartChart.patient.entity.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;
import java.util.Optional;


@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;


    /**
     * 환자 회원가입
     * @param request
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<Message> register (
            @RequestBody PatientJoinRequest request
    ) {

        // db insert
         service.register(request);


         // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        message.setCode(200);
        message.setMessage("성공");

        return new ResponseEntity<>(message, headers, HttpStatus.OK); // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함

    }




    /**
     * 환자 로그인
     * @param request
     * @param servletRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Message> authenticate (
            @RequestBody PatientLoginRequest request,
            HttpServletRequest servletRequest
    ) {

        // token
        AuthenticationResponse authenticationResponse = service.authenticate(request);

        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        if (authenticationResponse != null) {
            message.setCode(200);
            message.setMessage("성공");
            message.setData("token : " + authenticationResponse);


            // db 환자 정보
            Optional<Patient> patient = service.findByEmail(request.getEmail());
            if(patient.isPresent()) {    // Optional에서 값을 가져올 때에는 .isPresent() 메소드로 값의 존재 여부를 먼저 확인
                // 세션 생성
                HttpSession session = servletRequest.getSession();
                session.setAttribute("patientId", patient.get().getId()); // .get().getId()는 주로 Java의 스트림(Stream)이나 Optional에서 값을 추출하고 해당 객체의 ID 값을 가져오는 용도로 사용되는 코드 패턴
                session.setAttribute("patientEmail", patient.get().getEmail());
                session.setAttribute("patientName", patient.get().getName());
            }
        } else {
            message.setCode(500);
            message.setMessage("관리자에게 문의해주시기 바랍니다.");
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);   // ResponseEntity.ok() - 성공을 의미하는 OK(200 code)와 함께 user 객체를 Return 하는 코드
    }





}
