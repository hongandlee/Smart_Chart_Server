package com.smartChart.survey.entity;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@ToString // 객체 예쁘게 출력
@Getter
@AllArgsConstructor // 모든 값이 있는 생성자
@NoArgsConstructor // 기본 생성자
@Table(name = "sample_answer") //엔티티와 매핑할 테이블을 지정.
@Entity
public class Sample_answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    private int id;

    @Column(name = "yes", nullable = false)
    private String yes;

    @Column(name = "no", nullable = false)
    private String no;

    @Column(name = "unawareness", nullable = false)
    private String unawareness;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updatedAt")
    private Date updatedAt;


}
