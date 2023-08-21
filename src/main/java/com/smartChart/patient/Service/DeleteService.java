//package com.smartChart.patient.Service;
//
//import com.smartChart.auth.AuthenticationResponse;
//import com.smartChart.patient.dto.PatientJoinRequest;
//import com.smartChart.patient.dto.PatientLoginRequest;
//import com.smartChart.patient.entity.Patient;
//import com.smartChart.patient.entity.Role;
//import com.smartChart.patient.repository.PatientRepository;
//import com.smartChart.security.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//
//@Service
//public class PatientService {
//
//
//    @Autowired
//    PatientRepository patientRepository;
//
//    @Autowired
//    JwtTokenUtil jwtTokenUtil;
//
//
//
//    @Transactional
//    // 회원가입
//    public AuthenticationResponse join (PatientJoinRequest request) {
//        // 토큰 가져오기
//        var jwtToken = jwtTokenUtil.createToken(request.getEmail());
//
//        var patient = Patient.builder()
//                // Patient
//                .email(request.getEmail())
//                .password(jwtToken) // 알고리즘으로 비밀번호 저장
//                .name(request.getName())
//                .gender(request.getGender())
//                .age(request.getAge())
//                .phoneNumber(request.getPhoneNumber())
//                .role(Role.PATIENT)
//                .build();
//        patientRepository.save(patient);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();
//
//    }
//
//
//
//    // 로그인
//    public AuthenticationResponse login (PatientLoginRequest request) {
//
//        var patient = patientRepository.findByEmail(request.getEmail())
//                .orElseThrow();
//
//        if (request.getPassword().equals(patientRepository.getPass))
//        // 토큰 생성
//        var token = jwtTokenUtil.createToken(request.getEmail());
//
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .build();
//    }
//
//
//
//}
