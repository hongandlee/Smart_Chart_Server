package com.smartChart.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {


    // 자바 버전 15이상만 가능
    @Query("""
    select t from Token t inner join Patient p on t.patient.id = p.id
    where p.id = :patientId and (t.expired = false  or t.revoked = false) 
    """)
    List<Token> findAllValidTokensByPatient(@Param("patientId")Integer patientId);


    Optional<Token> findByToken(@Param("token")String token);


}
