package com.smartChart.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

// binding
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration  {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers( "/patient/**")
                .permitAll()
                .antMatchers( "/doctor/**")
                .permitAll()
                .antMatchers("/","/css/**","/images/**","/js/**","/image/**","/login/oauth2/**","/auth/**", "/websocket/**", "/static/**").permitAll()
                .antMatchers("/login", "/join").permitAll() // 로그인, 회원가입 접근 가능
//
//                .antMatchers(GET,"/patient/**").hasAuthority(PATIENT_READ.name())
//                .antMatchers(POST,"/patient/**").hasAuthority(PATIENT_CREATE.name())
//                .antMatchers(PUT,"/patient/**").hasAuthority(PATIENT_UPDATE.name())
//                .antMatchers(DELETE,"/patient/**").hasAuthority(PATIENT_DELETE.name())
//
//
//                .antMatchers( "/doctor/**").hasRole(DOCTOR.name())
//
//                .antMatchers(GET,"/doctor/**").hasAuthority(DOCTOR_READ.name())
//                .antMatchers(POST,"/doctor/**").hasAuthority(DOCTOR_CREATE.name())
//                .antMatchers(PUT,"/doctor/**").hasAuthority(DOCTOR_UPDATE.name())
//                .antMatchers(DELETE,"/doctor/**").hasAuthority(DOCTOR_DELETE.name())

                .anyRequest().authenticated() // 위의 경로 이외에는 모두 인증된 사용자만 접근 가능
                .and()
                .oauth2Login()

                .and()
                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/patient/sign_out")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(
                ((request, response, authentication) -> SecurityContextHolder.clearContext()))
                .and();


        return http.build();
    }



}
