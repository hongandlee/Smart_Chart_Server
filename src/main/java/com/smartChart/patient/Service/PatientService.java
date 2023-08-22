
package com.smartChart.patient.Service;


import com.smartChart.auth.AuthenticationResponse;
import com.smartChart.config.JwtService;
import com.smartChart.patient.dto.RequestDto.PatientJoinRequest;
import com.smartChart.patient.dto.RequestDto.PatientLoginRequest;
import com.smartChart.patient.entity.Patient;
import com.smartChart.patient.entity.Role;
import com.smartChart.patient.repository.PatientRepository;
import com.smartChart.token.Token;
import com.smartChart.token.TokenRepository;
import com.smartChart.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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


    /**
     * 회원가입
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

        // 유저가 올바르지 않다면...
        var patient = patientRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(patient);
        revokeAllPatientTokens(patient); // 모든 사용자 토큰 철회
        savePatientToken(patient, jwtToken); // 사용자 토큰 저장
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    /**
     * 모든 환자의 토큰을 철회하기
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


    // select
    public Optional<Patient> findByEmail(String email) { return patientRepository.findByEmail(email);
    }


    public Patient findEmailByEmail(String email) {
     return patientRepository.findEmailByEmail(email);
    }
}
