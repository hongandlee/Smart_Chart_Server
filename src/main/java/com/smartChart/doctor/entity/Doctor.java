package com.smartChart.doctor.entity;


import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@ToString // 객체 예쁘게 출력
@Getter
@AllArgsConstructor // 모든 값이 있는 생성자
@NoArgsConstructor // 기본 생성자
@Builder(toBuilder = true) // 수정 시 기존 객체 그대로, 세팅한 값만 변경
@Table(name = "doctor") //엔티티와 매핑할 테이블을 지정.
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    private int id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "salt", nullable = false)
    private String salt;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "phoneNumber", nullable = false)
    private int phoneNumber;

    @Column(name = "hospitalName", nullable = false)
    private String hospitalName;

    @Column(name = "hospitalAddress", nullable = false)
    private String hospitalAddress;

    @Column(name= "mapx", nullable = false)
    private int  mapx;

    @Column(name= "mapy", nullable = false)
    private int mapy;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "hospitalPhoneNumber")
    private Integer hospitalPhoneNumber;

    @Lob
    @Column(name = "hospitalIntroduce")
    private String hospitalIntroduce;

    @Lob
    @Column(name = "hospitalProfileURL")
    private String hospitalProfileURL;

    @Enumerated(EnumType.STRING)
    private Role role;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updatedAt")
    private Date updatedAt;


}
