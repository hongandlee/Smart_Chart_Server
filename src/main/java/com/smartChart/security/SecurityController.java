//package com.smartChart.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/security")
//public class SecurityController {
//
//    @Autowired
//    private SecurityService securityService;
//
//
//    // 원래는 post 방식으로 아이디, 비번 같이 던져주는 것을 해야함
//    @GetMapping("/create/token")
//    public Map<String, Object> createToken(@RequestParam(value = "subject") String subject) {
//        String token = securityService.createToken(subject, (2*1000*60)); // 2분
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("result", token);
//        return map;
//    }
//
//    @GetMapping("/get/subject")
//    public Map<String, Object> getSubject(@RequestParam(value = "token") String token) {
//        String subject = securityService.getSubject(token);
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("result", subject);
//        return map;
//    }
//
//}
