package com.smartChart.doctor;


import com.smartChart.Response.Message;
import com.smartChart.config.Encrypt;
import com.smartChart.doctor.dto.DoctorJoinRequest;
import com.smartChart.doctor.dto.DoctorLoginRequest;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
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

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;


    /**
     * 회원가입
     * @param request
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<Message> register(
            @RequestBody DoctorJoinRequest request
    ) {

        // salt 생성
        String salt = Encrypt.getSalt();
        // 최종 password 생성
        String saltPassword = Encrypt.getEncrypt(request.getPassword(), salt);


        // db insert
        doctorService.addDoctor(
                request.getEmail(), saltPassword, salt, request.getName(), request.getGender(),
                request.getAge(), request.getPhoneNumber(), request.getHospitalName(),
                request.getHospitalAddress(), request.getHospitalPhoneNumber(), request.getHospitalIntroduce(),
                request.getHospitalProfileURL(), request.getRole());

        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();
        message.setCode(200);
        message.setMessage("성공");


        return new ResponseEntity<>(message, headers, HttpStatus.OK);

        }


    /**
     * 로그인
     * @param request
     * @param servletRequest
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<Message> authenticate(
            @RequestBody DoctorLoginRequest request,
            HttpServletRequest servletRequest
    ) {

        //		비밀번호 인증
//		SHA-256(입력받은 비밀번호 + 유저 디비 테이블의 Salt값)과 / 유저테이블에 이미 생성된 해시 값이 같으면 인증 성공
//      로그인 할때 ID로 salt값을 조회하여 입력한 비밀번호와 Salt값을 다시 암호화하여 비밀번호 체킹.

        // db에서 salt 정보 가져오기
        Doctor prelogin = doctorService.getDoctorByEmail(request.getEmail());
        // salt db
        String salt = prelogin.getSalt();
        // 최종 password make - 패스워드 + db 솔트 값 합치기
        String saltPassword = Encrypt.getEncrypt(request.getPassword(), salt);


        // select null or 1행   // ** select - saltPassword로 해야함.
        Doctor doctor = doctorService.getUserByLoginIdPassword(request.getEmail(), saltPassword);



        // message
        Message message = new Message();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if (doctor != null) {
            message.setCode(200);
            message.setMessage("성공");

            // 세션에 유저 정보
            HttpSession session = servletRequest.getSession();
            session.setAttribute("doctorId", doctor.getId());
            session.setAttribute("doctorEmail", doctor.getEmail());
            session.setAttribute("doctorHospitalName", doctor.getHospitalName());
            session.setAttribute("doctorHospitalPhoneNumber", doctor.getHospitalPhoneNumber());

        } else {
            message.setCode(500);
            message.setMessage("관리자에게 문의해주시기 바랍니다.");
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }



    @RequestMapping("/sign_out")
    public String singOut (HttpSession session) {

        session.removeAttribute("doctorId");
        session.removeAttribute("doctorEmail");
        session.removeAttribute("doctorHospitalName");
        session.removeAttribute("doctorHospitalPhoneNumber");

        // 로그인 화면으로 이동
        return "redirect:/doctor/login-view";
    }
}








//
//
//import com.smartChart.Response.Message;
//import com.smartChart.doctor.dto.DoctorJoinRequest;
//import com.smartChart.doctor.dto.DoctorLoginRequest;
//import com.smartChart.doctor.service.DoctorService;
//import com.smartChart.auth.AuthenticationResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.nio.charset.Charset;
//
//@RestController
//@RequestMapping("/doctor")
//@RequiredArgsConstructor
//public class DoctorController {
//
//    private final DoctorService service;
//
//
//    @PostMapping("/join")
//    public ResponseEntity<Message> register(
//            @RequestBody DoctorJoinRequest request
//    ) {
//
//        // db insert
//        service.register(request);
//
//
//        // message
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        Message message = new Message();
//
//        message.setCode(200);
//        message.setMessage("성공");
//
//        return new ResponseEntity<>(message, headers, HttpStatus.OK); // ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함
//
//    }
//
//
//    @PostMapping("/login")
//    public ResponseEntity<Message> authenticate(
//            @RequestBody DoctorLoginRequest request
//    ) {
//
//        // db
//        AuthenticationResponse authenticationResponse = service.authenticate(request);
//
//        // message
//        HttpHeaders headers = new HttpHeaders();
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
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);   // ResponseEntity.ok() - 성공을 의미하는 OK(200 code)와 함께 user 객체를 Return 하는 코드
//    }
//
//
//    @PutMapping ("/patch")
//    public String patch() {
//            return"Put:: doctor controller";
//    }
//
//    @GetMapping("/get")
//    public String get() {
//        return"Get:: doctor controller";
//    }
//
//
//    @DeleteMapping("/delete")
//    public String delete() {
//        return"Delete:: doctor controller";
//    }
//
//}
