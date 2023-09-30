package com.smartChart.websocket.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
@Slf4j
public class ChatController {




    @MessageMapping("/chat.sendMessage") // @MessageMapping 설정한 url 매핑으로 클라이언트로부터 요청 메시지를 받으면 @SendTo 설정한 구독 클라이언트들에게 메시지를 보냅니다.
    @SendTo("/topic/public")  // @SendTo : 어디로 보낼 것이지.. topic은 WebSocketConfig에서 옴.
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage  // @Payload - 페이로드는 전송되는 데이터 자체를 지칭, 전송 목적이 되는 데이터만 포함하고 그 데이터와 함께 전송되는 헤더나 메타 데이터는 제외
    ) {
        return chatMessage;
    }




    // 유저네임 추가


    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage,
            SimpMessageHeaderAccessor headerAccessor // SimpMessageHeaderAccessor -  메시지 헤더 작업을 위한 기본 클래스, 대상, 메시지 유형(예: 게시, 구독 등), 세션 ID 등과 같은 프로토콜 전체에서 공통적인 특정 값에 대한 균일한 액세스를 제공
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
}
