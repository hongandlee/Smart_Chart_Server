package com.smartChart.doctor.repository;


import com.smartChart.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

  Doctor findByEmail(String email);


  Doctor findByEmailAndPassword(String email, String password);

}
