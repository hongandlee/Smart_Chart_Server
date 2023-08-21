//package com.smartChart.security;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("")
//public class SecurityController {
//
//    @Autowired
//    private JwtTokenUtil securityService;
//
//
//    /**
//     * 회원가입
//     * @param subject
//     * @return
//     */
//    // 원래는 post 방식으로 아이디, 비번 같이 던져주는 것을 해야함
//    @PostMapping(")
//    public Map<String, Object> createToken(@RequestParam(value = "subject") String subject) {
//        String token = securityService.createToken(subject, (2*1000*60)); // 2분
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("result", token);
//        return map;
//    }
//
//
//    /**
//     * 로그인
//     * @param token
//     * @return
//     */
//    @GetMapping("/get/subject")
//    public Map<String, Object> getSubject(@RequestParam(value = "token") String token) {
//        String subject = securityService.getSubject(token);
//        Map<String, Object> map = new LinkedHashMap<>();
//        map.put("result", subject);
//        return map;
//    }
//
//}
