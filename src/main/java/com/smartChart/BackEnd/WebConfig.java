package com.smartChart.BackEnd;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 개발 환경에서의 크로스 도메인 이슈를 해결하기 위한 코드로
     * 운영 환경에 배포할 경우에는 15~18행을 주석 처리합니다.
     *
     * ※크로스 도메인 이슈: 브라우저에서 다른 도메인으로 URL 요청을 하는 경우 나타나는 보안문제
     */


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080/", "http://localhost:3000","http://13.125.227.145:8080","https://smartchart.vercel.app/","ws://localhost:8080/ws/chat/") // 허용할 출처
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT") // 허용할 HTTP method
                .allowCredentials(true) // 쿠키 인증 요청 허용
                .maxAge(10800); // 원하는 시간만큼 pre-flight 리퀘스트를 캐싱
    }


}