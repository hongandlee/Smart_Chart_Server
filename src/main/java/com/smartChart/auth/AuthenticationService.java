package com.smartChart.auth;


import com.smartChart.config.JwtService;
import com.smartChart.patient.entity.Patient;
import com.smartChart.patient.entity.Role;
import com.smartChart.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PatientRepository patientRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
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
        var jwtToken = jwtService.generateToken(patient);  //  jwtService에서 token 가져오기
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
        
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
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
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
