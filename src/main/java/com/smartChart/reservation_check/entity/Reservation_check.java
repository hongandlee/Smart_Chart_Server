package com.smartChart.reservation_check.entity;

import com.smartChart.doctor.entity.Doctor;
import com.smartChart.reservation.entity.Reservation;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation_check",
        uniqueConstraints = {
                @UniqueConstraint(name = "ReservationId_DoctorId_UNIQUE", columnNames = {"reservationId", "doctorId"})
        }
)
public class Reservation_check {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 생성을 데이터베이스에게 위임하는 방식으로 id값을 따로 할당하지 않아도 데이터베이스가 자동으로 AUTO_INCREMENT를 하여 기본키를 생성
    private int id;

    @ManyToOne
    @JoinColumn(name="reservationId")
    private Reservation reservation;
    @ManyToOne
    @JoinColumn(name="doctorId")
    private Doctor doctor;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;
}
