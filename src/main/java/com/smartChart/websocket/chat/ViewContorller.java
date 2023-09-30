package com.smartChart.websocket.chat;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ViewContorller {

    // 채팅 리스트 화면
    @GetMapping("/chatting-view")
    public String chatting(Model model) {

        return "index";
    }
}
