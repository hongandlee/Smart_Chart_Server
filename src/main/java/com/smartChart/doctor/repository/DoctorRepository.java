package com.smartChart.doctor.repository;


import com.smartChart.doctor.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

  Doctor findByEmail(String email);


  Doctor findByEmailAndPassword(String email, String password);



  @Query(value = "SELECT d.id, d.hospitalName, d.hospitalAddress, d.mapx, d.mapy, d.category, d.hospitalPhoneNumber, d.hospitalIntroduce, d.hospitalProfileURL FROM doctor d WHERE d.category = :category", nativeQuery = true)
  public List<HospitalInterface> findDoctorByCategory(@Param("category") String category);



  Doctor findById(int id);


}
