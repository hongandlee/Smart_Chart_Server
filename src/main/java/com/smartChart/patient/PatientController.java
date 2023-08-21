package com.smartChart.patient;


import com.smartChart.Response.Message;
import com.smartChart.auth.AuthenticationResponse;
import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.dto.PatientJoinRequest;
import com.smartChart.patient.dto.PatientLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;


@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService service;




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


    @PostMapping("/login")
    public ResponseEntity<Message> authenticate (
            @RequestBody PatientLoginRequest request
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
        } else {
            message.setCode(500);
            message.setMessage("관리자에게 문의해주시기 바랍니다.");
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);   // ResponseEntity.ok() - 성공을 의미하는 OK(200 code)와 함께 user 객체를 Return 하는 코드
    }




}
