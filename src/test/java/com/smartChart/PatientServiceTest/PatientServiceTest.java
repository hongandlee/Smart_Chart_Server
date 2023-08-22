package com.smartChart.PatientServiceTest;


import com.smartChart.patient.Service.PatientService;
import com.smartChart.patient.dto.RequestDto.PatientJoinRequest;
import com.smartChart.patient.dto.RequestDto.PatientLoginRequest;
import com.smartChart.patient.entity.Patient;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PatientServiceTest {


    Logger logger = LoggerFactory.getLogger(this.getClass()); // // import org.mybatis.logging.LoggerFactory; - 사용 불가.

   @Autowired
    PatientService patientService;

   @Mock // 테스트를 위해 가상의 요청 객체를 만들어서 사용하는 방법입니다. 모의 라이브러리(Mocking Library)를 사용하여 요청 객체의 동작을 모방하고, 서비스 메서드에 전달
   private PatientLoginRequest request;

   @Mock
   private PatientJoinRequest joinRequest;





//    @DisplayName("회원가입")
//    @Test
    void 회원가입() {
        logger.info("######### 회원가입 ");
        patientService.register(joinRequest);
    }



//    @Transactional
//    @Test
    void 아이디_중복체크 () {
        logger.info("###### 아이디 중복 체크");
        Patient patient = patientService.findEmailByEmail("fork@bb.com");
        assertNotNull(patient); // null이 아님을 시험하기 위해 assertNotNull 메소드
    }




//    @DisplayName("로그인")
//    @Transactional
//    @Test
    void login() {
        logger.info("##### 로그인 #####");
        patientService.authenticate(request);
    }





}
