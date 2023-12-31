package com.smartChart.doctor.service;

import com.smartChart.config.Encrypt;
import com.smartChart.doctor.dto.RequestDto.DoctorHospitalInterface;
import com.smartChart.doctor.dto.RequestDto.DoctorMypageInterface;
import com.smartChart.doctor.entity.Doctor;
import com.smartChart.doctor.entity.Role;
import com.smartChart.doctor.repository.DoctorRepository;
import com.smartChart.doctor.repository.HospitalInterface;
import com.smartChart.patient.dto.ResponseDto.MailResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {



    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final DoctorRepository doctorRepository;

    private final PasswordEncoder passwordEncoder;


    private final JavaMailSender mailSender;



    // 회원가입
    public Doctor addDoctor(
            String email,
            String password,
            String salt,
            String name,
            String gender,
            int age,
            int phoneNumber,
            String hospitalName,
            String hospitalAddress,
            int mapx,
            int mapy,
            String category,
            int hospitalPhoneNumber,
            String hospitalIntroduce,
            String hospitalProfileURL,
            Role role
    ) {
        return doctorRepository.save(Doctor.builder()
                .email(email)
                .password(password)
                .salt(salt)
                .name(name)
                .gender(gender)
                .age(age)
                .phoneNumber(phoneNumber)
                .hospitalName(hospitalName)
                .hospitalAddress(hospitalAddress)
                .mapx(mapx)
                .mapy(mapy)
                .category(category)
                .hospitalPhoneNumber(hospitalPhoneNumber)
                .hospitalIntroduce(hospitalIntroduce)
                .hospitalProfileURL(hospitalProfileURL)
                .role(Role.DOCTOR)
                .build());


    }




    // 메일 내용을 생성하고 임시 비밀번호로 회원 비밀번호를 변경
    public MailResponse createMailAndChangePassword(String doctorEmail) {
        String str = getTempPassword();
        MailResponse mailRequest = new MailResponse();
        mailRequest.setAddress(doctorEmail);
        mailRequest.setTitle("Smart Chart 임시비밀번호 안내입니다." );
        mailRequest.setMessage("안녕하세요." + "\n" +"Smart Chart 변경된 비밀번호는 " + str + "입니다.");
        updatePassword(str, doctorEmail);
        return mailRequest;
    }




    //임시 비밀번호로 업데이트
    public void updatePassword(String str, String doctorEmail) {  // str : 임시 비밀번호
        String updatePassword = str;

        Doctor doctor = doctorRepository.findByEmail(doctorEmail);
        // salt 생성
        String salt = Encrypt.getSalt();
        // 최종 password 생성
        String saltPassword = Encrypt.getEncrypt(updatePassword, salt);

        // db
        doctor = doctor.toBuilder() // toBuilder는 기존값 유지하고 일부만 변경
                .password(saltPassword)
                .salt(salt)
                .build();
        doctorRepository.save(doctor);
    }





    // 랜덤함수로 임시비밀번호 구문 만들기
    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        // 문자 배열 길이의 값을 랜덤으로 10개를 뽑아 구문을 작성함
        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }



    // 메일보내기
    public void mailSend(MailResponse mailRequest) {
        logger.info("################### 전송 완료!");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailRequest.getAddress());
        message.setSubject(mailRequest.getTitle());
        message.setText(mailRequest.getMessage());
        message.setFrom("dbfl9532@naver.com");
        message.setReplyTo("dbfl9532@naver.com");
        logger.info("######################  message"+message);
        mailSender.send(message);
    }






    // select
    public Doctor getDoctorByEmail(String email) {

        return doctorRepository.findByEmail(email);
    }


    // select - 로그인
    public Doctor getUserByLoginIdPassword(String email, String password) {
        return doctorRepository.findByEmailAndPassword(email, password);
    }





    // select - 카테고리별 의사 리스트
    public List<HospitalInterface> findDoctorByCategory (String category) {
        return doctorRepository.findDoctorByCategory(category);
    }


    // select - 의사 1명 조회
    public Doctor findById (int id) {
        return doctorRepository.findById(id);
    }


    // 의사 마이페이지 조회
    public List<DoctorMypageInterface> selectInfoByDoctorId(int doctorId) { return doctorRepository.findDoctorInfoById(doctorId);}

    // 의사 병원페이지 조회
    public List<DoctorHospitalInterface> selectHospitalByDoctorId(int doctorId) { return doctorRepository.findHospitalInfoById(doctorId);}

    // 의사 마이페이지 수정
    public Doctor updateDoctorById(Integer doctorId, String name, String gender, Integer age, Integer phoneNumber) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if(doctor != null) {
            doctor = doctor.toBuilder()
                    .name(name)
                    .gender(gender)
                    .age(age)
                    .phoneNumber(phoneNumber)
                    .build();
            doctor = doctorRepository.save(doctor);
        }

        return doctor;
    }


    public Doctor updateHospitalById(Integer doctorId, String hospitalName, int hospitalPhoneNumber, String hospitalIntroduce) {
        Doctor doctor = doctorRepository.findDoctorById(doctorId);
        if(doctor != null) {
            doctor = doctor.toBuilder()
                    .hospitalName(hospitalName)
                    .hospitalPhoneNumber(hospitalPhoneNumber)
                    .hospitalIntroduce(hospitalIntroduce)
                    .build();
            doctor = doctorRepository.save(doctor);
        }

        return doctor;
    }





} // end




