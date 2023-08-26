package com.smartChart.DoctorServiceTest;


import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.service.DoctorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DoctorServiceTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    DoctorService doctorService;

    // @DisplayName("회원가입")
//    @Test
//    void 회원가입() {
//        logger.info("######### 회원가입 ");
//        doctorService.addDoctor("library@doctor.com","password", "salt", "name",
//                "gender", "22", "01011111111","name","address","2222");
//    }



//    @Transactional
//    @Test
    void 아이디_중복체크 () {
        logger.info("###### 아이디 중복 체크");
        Doctor doctor = doctorService.getDoctorByEmail("doctor@doctor.com");

    }


//    @DisplayName("로그인")
//    @Test
    void login() {
        logger.info("##### 로그인 #####");
        doctorService.getUserByLoginIdPassword("dbfl@dbfl.com","dbfl");
    }



//    @DisplayName("메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경")
//    @Transactional
//    @Test
    void createMailAndChangePassword() {
        logger.info("########### 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경");
        doctorService.createMailAndChangePassword("library@doctor.com");

    }



//    @DisplayName("임시 비밀번호로 업데이트")
//    @Transactional
//    @Test
    void updatePassword() {
        logger.info("##############임시 비밀번호로 업데이트");
        doctorService.updatePassword("23fsfdfsfsdfsdf", "library@doctor.com");
    }




//    @DisplayName("랜덤함수로 임시비밀번호 구문 만들기")
//    @Transactional
//    @Test
    void getTempPassword() {
        logger.info("################# 랜덤함수로 임시비밀번호 구문 만들기");
        doctorService.getTempPassword();
    }





}
