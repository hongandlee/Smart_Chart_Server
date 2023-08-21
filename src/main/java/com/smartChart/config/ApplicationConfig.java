package com.smartChart.config;


import com.smartChart.doctor.repository.DoctorRepository;
import com.smartChart.patient.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


// to get the user from Database
@Configuration //@Configuration이라고 하면 설정파일을 만들기 위한 애노테이션 or Bean을 등록하기 위한 애노테이션
@RequiredArgsConstructor //새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입)
public class ApplicationConfig {


    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;




    // 등록되 유저가 아닐 경우
    @Bean
    public UserDetailsService userDetailsService() {
        return patient -> (UserDetails) patientRepository.findByEmail(patient)
                .orElseThrow(() -> new UsernameNotFoundException("등록된 유저가 아닙니다."));
    }

//    @Bean
//    public UserDetailsService doctorDetailsService() {
//        return doctor -> (UserDetails) doctorRepository.findByEmail(doctor)
//                .orElseThrow(() -> new UsernameNotFoundException("등록된 유저가 아닙니다."));
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(doctorDetailsService());
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
