package com.smartChart.doctor.service;


import com.smartChart.auth.AuthenticationResponse;
import com.smartChart.config.JwtService;
import com.smartChart.doctor.dto.DoctorJoinRequest;
import com.smartChart.doctor.dto.DoctorLoginRequest;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.entity.Role;
import com.smartChart.doctor.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    /**
     * 회원가입
     * @param request
     * @return
     */
    public AuthenticationResponse register(DoctorJoinRequest request) {
        var doctor = Doctor.builder()
                // Doctor
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .gender(request.getGender())
                .age(request.getAge())
                .phoneNumber(request.getPhoneNumber())
                .hospitalName(request.getHospitalName())
                .hospitalAddress(request.getHospitalAddress())
                .hospitalPhoneNumber(request.getHospitalPhoneNumber())
                .role(Role.DOCTOR)
                .build();
        doctorRepository.save(doctor);
        var jwtToken = jwtService.generateToken(doctor);  //  jwtService에서 token 가져오기
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }


    /**
     * 로그인
     * @param request
     * @return
     */
    public AuthenticationResponse authenticate(DoctorLoginRequest request) {
        // 유저가 올바르다면..
        authenticationManager.authenticate( // to take object to users' token
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 유저가 올바르지 않다면...
        var doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(doctor);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


}
