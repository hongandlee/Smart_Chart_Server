package com.smartChart.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

//    @Autowired
//    PatientMapper patientMapper;


    @ResponseBody
    @RequestMapping("/test1")  // String return  test
    public String test1() {
        return "Hello world";
    }

    @ResponseBody
    @RequestMapping("/test2")  // Map return Json test
    public Map<String, Object> test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 13);
        map.put("c", 23);
        map.put("d", 2);

        return map;
    }


//    @ResponseBody
//    @RequestMapping("/test4") // db 연동 test
//
//    public List<Map<String, Object>> test4 (){
//        return patientMapper.selectPatientList();
//    }
}
