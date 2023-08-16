package com.smartChart.treatment.entity;


import com.smartChart.reservation.entity.Reservation;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@ToString // 객체 예쁘게 출력
@Getter
@AllArgsConstructor // 모든 값이 있는 생성자
@NoArgsConstructor // 기본 생성자
@Builder(toBuilder = true) // 수정 시 기존 객체 그대로, 세팅한 값만 변경
@Table(name = "treatment") //엔티티와 매핑할 테이블을 지정.
@Entity
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    private int id;

    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;

    @Column(name = "medicalHistory", nullable = false)
    private String medicalHistory;

    @Column(name = "mainSymptoms", nullable = false)
    private String mainSymptoms;

    @Column(name = "currentSymptoms", nullable = false)
    private String currentSymptoms;

    @Column(name = "treatmentPlan", nullable = false)
    private String treatmentPlan;

    @Column(name = "note", nullable = false)
    private String note;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updatedAt")
    private Date updatedAt;

}
