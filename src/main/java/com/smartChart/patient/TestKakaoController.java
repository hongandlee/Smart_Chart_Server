package com.smartChart.patient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class TestKakaoController {

    @GetMapping("/")
    public String index() {
        return "index"; // 이 부분은 "index.html"을 의미
    }
}