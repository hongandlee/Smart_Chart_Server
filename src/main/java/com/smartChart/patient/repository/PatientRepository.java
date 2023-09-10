package com.smartChart.patient.repository;


import com.smartChart.patient.dto.RequestDto.PatientMypageInterface;
import com.smartChart.patient.dto.RequestDto.PatientMypageListInterface;
import com.smartChart.patient.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByEmail(String email); //  Optional<T>는 null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NPE가 발생하지 않도록 도와준다.

    @Query("select p from Patient p where p.email = :email")
    Patient findEmailByEmail(@Param("email") String email);


    Patient findPatientById(Integer id);



    // 환자 마이페이지 환자 조회
    @Query(nativeQuery = true, value =  "SELECT C.name, C.phoneNumber, C.gender, C.age\n" +
            "from patient AS C\n" +
            "where C.id = :patientId"
    )
    List<PatientMypageInterface> findById(@Param("patientId") int patientId);




    // 환자 마이페이지 리스트
    @Query(nativeQuery = true, value =  "select B.id, A.hospitalName, B.reservationDate, B.reservationTime, B.reservationStatus\n" +
            "from reservation AS B\n" +
            "join doctor AS A\n" +
            "on B.doctorId = A.id\n" +
            "join patient AS C\n" +
            "on C.id = B.patientId\n" +
            "where C.id = :patientId"
    )
    public List<PatientMypageListInterface> finListByPatientId(
            @Param("patientId") int patientId);

}
