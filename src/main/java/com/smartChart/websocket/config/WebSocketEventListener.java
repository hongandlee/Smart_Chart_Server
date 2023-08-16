package com.smartChart.websocket.config;

import com.smartChart.websocket.chat.ChatMessage;
import com.smartChart.websocket.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component // @Component 어노테이션은 기본적으로 타입기반의 자동주입 어노테이션이다. @Autowired, @Resource와 비슷한 기능을 수행
@Slf4j // 로깅에 대한 추상 레이어를 제공하는 인터페이스의 모음
@RequiredArgsConstructor //새로운 필드를 추가할 때 다시 생성자를 만들어서 관리해야하는 번거로움을 없애준다. (@Autowired를 사용하지 않고 의존성 주입
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate; // 세션이 인증되지 않은 경우 사용자 이름 대신 세션 ID(알려진 경우)를 전달할 수도 있습니다. 이 시나리오에서는 헤더를 허용하는 오버로드된 메서드 중 하나를 사용하여 헤더가 sessionId적절하게 설정되었는지 확인


    // 유저가 나갔는지 안나갔는지 알려주는 것.
    @EventListener   // 서비스 간의 강한 의존성을 줄이기 위함'
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()); //  SimpMessageHeaderAccessor일부 STOMP 헤더(예: 대상, 콘텐츠 유형 등)를 기반으로 하는 공통 처리 헤더를 관리
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            var chatMessage = ChatMessage.builder()  // 객체를 생성할 수 있는 빌더를 builder() 함수를 통해 얻고 거기에 셋팅하고자 하는 값을 셋팅하고 마지막에 build()를 통해 빌더를 작동 시켜 객체를 생성
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            messagingTemplate.convertAndSend("/topic/public", chatMessage);  // convertAndSend() 는 Object 타입 객체를 인자로 받아 내부적으로 Message 타입으로 변환한다.  convertAndSend() 메서드는 전송될 객체를 인자로 직접 전달한다. 즉 해당 객체가 Message 객체로 변환되어 전송
        }
    }

}
