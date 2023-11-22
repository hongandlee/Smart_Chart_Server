
package com.smartChart.patient.Service;


import com.smartChart.auth.AuthenticationResponse;
import com.smartChart.config.JwtService;
import com.smartChart.patient.dto.RequestDto.PatientJoinRequest;
import com.smartChart.patient.dto.RequestDto.PatientLoginRequest;
import com.smartChart.patient.dto.RequestDto.PatientMypageInterface;
import com.smartChart.patient.dto.RequestDto.PatientMypageListInterface;
import com.smartChart.patient.dto.ResponseDto.MailResponse;
import com.smartChart.patient.entity.Patient;
import com.smartChart.patient.entity.Role;
import com.smartChart.patient.repository.PatientRepository;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationRepository;
import com.smartChart.token.Token;
import com.smartChart.token.TokenRepository;
import com.smartChart.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PatientRepository patientRepository;

    private final TokenRepository tokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final ReservationRepository reservationRepository;

    private final JavaMailSender mailSender;






    /**
     * 회원가입
     *
     * @param request
     * @return
     */
    public AuthenticationResponse register(PatientJoinRequest request) {
        var patient = Patient.builder()
                // Patient
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.PATIENT)
                .oauth(request.getOauth())
                .build();
        patientRepository.save(patient);
        var savedPatient = patientRepository.save(patient);
        var jwtToken = jwtService.generateToken(patient);  //  jwtService에서 token 가져오기
        savePatientToken(savedPatient, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    /**
     * 로그인
     *
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(PatientLoginRequest request) {
        // 유저가 올바르다면..
        authenticationManager.authenticate( // to take object to users' token
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var patient = patientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("code: 500" + "message : 로그인 정보가 올바르지 않습니다.")); // 유저가 올바르지 않다면...

        var jwtToken = jwtService.generateToken(patient);
        revokeAllPatientTokens(patient); // 모든 사용자 토큰 철회
        savePatientToken(patient, jwtToken); // 사용자 토큰 저장
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    /**
     * 모든 환자의 토큰을 철회하기
     *
     * @param patient
     */
    private void revokeAllPatientTokens(Patient patient) {
        var validPatientTokens = tokenRepository.findAllValidTokensByPatient(patient.getId());
        if (validPatientTokens.isEmpty())
            return;
        validPatientTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });
        tokenRepository.saveAll(validPatientTokens);
    }


    /**
     * 환자 토큰 저장
     *
     * @param patient
     * @param jwtToken
     */
    private void savePatientToken(Patient patient, String jwtToken) {
        var token = Token.builder()  // 객체를 생성할 수 있는 빌더를 builder() 함수
                .patient(patient)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }


    /**
     * 카카오톡 로그인으로 회원찾기
     *
     * @param email
     * @return
     */
    @Transactional(readOnly = true)
    public Patient 회원찾기(String email) {
        Patient patient = patientRepository.findByEmail(email).orElseGet(() -> { // orElseGet 만약 회원을 찾았는데 없으면 빈 객체를 리턴해라.
            return new Patient(); // null이 아니고 빈 객체를 반환.
        });
        return patient;
    }


    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailResponse createMailAndChangePassword(String patientEmail) {
        String str = getTempPassword();
        MailResponse mailRequest = new MailResponse();
        mailRequest.setAddress(patientEmail);
        mailRequest.setTitle("Smart Chart 임시비밀번호 안내입니다.");
        mailRequest.setMessage("안녕하세요." + "\n" + "Smart Chart 변경된 비밀번호는 " + str + "입니다.");
        updatePassword(str, patientEmail);
        return mailRequest;
    }


    //임시 비밀번호로 업데이트
    public void updatePassword(String str, String patientEmail) {
        String updatePassword = str;
        String encodedPassword = passwordEncoder.encode(updatePassword);

        Patient patient = patientRepository.findEmailByEmail(patientEmail);
        patient = patient.toBuilder() // toBuilder는 기존값 유지하고 일부만 변경
                .password(encodedPassword)
                .build();
        patientRepository.save(patient);
    }


    // 랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword() {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


    // 메일보내기
    public void mailSend(MailResponse mailRequest) {
        logger.info("################### 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequest.getAddress());
        message.setSubject(mailRequest.getTitle());
        message.setText(mailRequest.getMessage());
        message.setFrom("dbfl9532@naver.com");
        message.setReplyTo("dbfl9532@naver.com");
        logger.info("######################  message" + message);
        mailSender.send(message);
    }


    // select
    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }


    public Patient findEmailByEmail(String email) {
        return patientRepository.findEmailByEmail(email);
    }

    public Patient findPatientById(Integer id) {

        return patientRepository.findPatientById(id);
    }


    // 마이페이지 조회
    public List<PatientMypageInterface> selectInfoByPatientId(int patientId) { return patientRepository.findById(patientId);}

    public List<PatientMypageListInterface> selectListByPatientId(int patientId) {return patientRepository.finListByPatientId(patientId);}


    // 마이페이지 업데이트
    public Patient updatePatientById(Integer patientId, String name, String gender, Integer age, Integer phoneNumber) {
        Patient patient = patientRepository.findPatientById(patientId);
        if(patient != null) {
            patient = patient.toBuilder()
                    .name(name)
                    .gender(gender)
                    .age(age)
                    .phoneNumber(phoneNumber)
                    .build();
            patient = patientRepository.save(patient);
        }

        return patient;
    }




    // 마이페이지 취소
    public Reservation deleteReservationById(int reservationId) {

        Reservation reservation = reservationRepository.findById(reservationId);
        if (reservation != null) {
            reservationRepository.delete(reservation);
        }
        return reservation;
    }
}
