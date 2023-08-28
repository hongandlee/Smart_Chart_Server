package com.smartChart.doctor;


import com.smartChart.Response.Message;
import com.smartChart.config.Encrypt;
import com.smartChart.doctor.dto.RequestDto.DoctorJoinRequest;
import com.smartChart.doctor.dto.RequestDto.DoctorLoginRequest;
import com.smartChart.doctor.dto.RequestDto.DoctorEmailRequest;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import com.smartChart.patient.dto.RequestDto.MailRequest;
import com.smartChart.patient.dto.ResponseDto.MailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     *
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
                request.getHospitalAddress(), request.getMapx(), request.getMapy(), request.getCategory(),
                request.getHospitalPhoneNumber(), request.getHospitalIntroduce(),
                request.getHospitalProfileURL(), request.getRole());

        // message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();
        message.setCode(200);
        message.setMessage("성공");


        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }





    /**
     * 로그인
     *
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





    /**
     * 로그아웃
     *
     * @param session
     * @return
     */
    @RequestMapping("/sign_out")
    public String singOut(HttpSession session) {

        session.removeAttribute("doctorId");
        session.removeAttribute("doctorEmail");
        session.removeAttribute("doctorHospitalName");
        session.removeAttribute("doctorHospitalPhoneNumber");

        // 로그인 화면으로 이동
        return "redirect:/doctor/login-view";
    }


    @RequestMapping("/check-email")
    public ResponseEntity<Message> check_Email(
            @RequestBody DoctorEmailRequest request
    ) {

        // db
        Doctor doctor = doctorService.getDoctorByEmail(request.getEmail());

        // message
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();

        if (doctor != null) {        // 중복
            message.setCode(300);
            message.setMessage("중복된 아이디입니다.");

        } else {                     // 중복이 아닐 때
            message.setCode(200);
            message.setMessage("사용가능한 아이디입니다.");
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }







    /**
     * 비밀번호 찾기
     * @param mailRequest
     * @return
     */
    @PostMapping("/sendEmail")
    public ResponseEntity<Message> sendEmail(
            @RequestBody MailRequest mailRequest) {


        // message
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        Message message = new Message();


        if(message != null) {        // 이메일을 찾았을 경우
            message.setCode(200);
            message.setMessage("이메일이 전송되었습니다.");
            MailResponse mailResponse = doctorService.createMailAndChangePassword(mailRequest.getEmail());
            doctorService.mailSend(mailResponse);

        } else {                     // 이메일을 찾지 못했을 경우
            message.setCode(404);
            message.setMessage("일치하는 회원이 없습니다.");
        }
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }




}