//package com.smartChart.patient;
//
//import com.smartChart.Response.Message;
//import com.smartChart.auth.AuthenticationResponse;
//import com.smartChart.patient.Service.PatientService;
//import com.smartChart.patient.dto.PatientJoinRequest;
//import com.smartChart.patient.dto.PatientLoginRequest;
//import com.smartChart.security.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.nio.charset.Charset;
//
//@RestController
//@RequestMapping("/patient")
//public class patientController {
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private PatientService patientService;
//
//
//    /**
//     * 회원가입
//     * @param request
//     * @return
//     */
//    @PostMapping("/join")
//    public ResponseEntity<Message> join (
//            @RequestBody PatientJoinRequest request
//            ) {
//        // db
//        patientService.join(request);
//
//        // message
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        Message message = new Message();
//
//        message.setCode(200);
//        message.setMessage("성공");
//
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    }
//
//
//
//    @PostMapping("/login")
//    public ResponseEntity<Message> login (
//            @RequestBody PatientLoginRequest request
//            ){
//
//        AuthenticationResponse authenticationResponse = patientService.login(request);
//
//        // message
//        HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        Message message = new Message();
//
//        if (authenticationResponse != null) {
//            message.setCode(200);
//            message.setMessage("성공");
//        } else {
//            message.setCode(500);
//            message.setMessage("관리자에게 문의해주시기 바랍니다.");
//        }
//
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    }
//
//}
