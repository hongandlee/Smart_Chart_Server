package com.smartChart.survey.entity;

import com.smartChart.patient.entity.Patient;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@ToString // 객체 예쁘게 출력
@Getter
@Setter
@AllArgsConstructor // 모든 값이 있는 생성자
@NoArgsConstructor // 기본 생성자
@Builder(toBuilder = true) // 수정 시 기존 객체 그대로, 세팅한 값만 변경
@Table(name = "survey") //엔티티와 매핑할 테이블을 지정.
@Entity
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    private int id;


    @ManyToOne
    @JoinColumn(name="questionId", nullable = false)
    private Sample_question sample_question;

    @ManyToOne
    @JoinColumn(name="patientId", nullable = false)
    private Patient patient;


    @Column(name = "answer", nullable = false)
    private String answer;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updatedAt")
    private Date updatedAt;



    public Survey( Patient patient, Sample_question sample_question, String answer) {
            this.patient = patient;
            this.sample_question = sample_question;
            this.answer = answer;
    }

}
