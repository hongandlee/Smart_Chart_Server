package com.smartChart.patient.entity;


import com.smartChart.token.Token;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Getter
@AllArgsConstructor // 모든 값이 있는 생성자
@NoArgsConstructor // 기본 생성자
@Builder(toBuilder = true)
@Table(name = "patient") //엔티티와 매핑할 테이블을 지정.
@Entity
public class Patient implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "email", nullable = false)  // nullable = false  -> not null 조건
    private String email;


    @Column(name = "password", nullable = false)
    private String password;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = true)
    private String gender;

    @Column(name = "age", nullable = true)
    private int age;

    @Column(name = "phoneNumber", nullable = true)
    private int phoneNumber;


    @Enumerated(EnumType.STRING)
    private Role role;


    private String oauth; // kakao, google


    @OneToMany(mappedBy = "patient")
    private List<Token> tokens;

    @UpdateTimestamp
    @Column(name="createdAt", updatable = false) // updatable는 update 시점에 막는 기능
    private Date createdAt;

    @UpdateTimestamp
    @Column(name="updatedAt")
    private Date updatedAt;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
