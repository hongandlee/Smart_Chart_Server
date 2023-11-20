package com.smartChart.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker // @EnableWebSocketMessageBroker 으로 Websocket 기능을 활성화시킨다.
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) { // registerStompEndpoints : SockJs Fallback을 이용해 노출할 STOMP endpoint를 설정한다.
        registry.addEndpoint("/ws/chat")  // sockJs 클라이언트가 Websocket 핸드셰이크를 하기 위해 연결할 endpoint를 지정할 수 있다.
                .setAllowedOrigins("*") // 원하는 도메인 추가  // 도메인 설정 나중에 추가 필요, 기존의 주석처리 되어있었음.
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {  // configureMessageBroker : 메시지 브로커에 관련된 설정을 한다
        registry.setApplicationDestinationPrefixes("/app");  // ApplicationDestinationPrefixes를 지정하면 대상 헤더가 시작되는 STOMP 메시지는 해당 클래스의 메서드로 라우팅된다.
        registry.enableSimpleBroker("/topic");
    }
}
