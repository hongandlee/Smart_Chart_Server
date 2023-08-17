//package com.smartChart.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//
//@Service
//public class SecurityService {
//    private static final String SECRET_KEY = "sdfsdfdsfsdfdsflkdsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsdfsjflkdsjlf";  // 이렇게 하면 x
//    // 유저의 비밀번호를 이용해서 만들 수 있다. ?
//
//    public String createToken(String subject, long expTime) { // subject: user 아이디
//        if(expTime<=0) {
//            throw new RuntimeException("만료시간이 0보다 커야합니다.");
//        }
//
//        // 토큰 만들기
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        byte[] secreteKeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
//        Key signingKey = new SecretKeySpec(secreteKeyBytes, signatureAlgorithm.getJcaName()); // key 생성
//
//        return Jwts.builder()
//                .setSubject(subject)
//                .signWith(signingKey, signatureAlgorithm)
//                .setExpiration(new Date(System.currentTimeMillis() + expTime)) // 만료 시간 설정
//                .compact();
//
//    }
//
//
//    public String getSubject(String token) {
//        Claims claims = Jwts.parserBuilder()  // claims : payload에 담길 정보
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .build()
//                .parseClaimsJws(token) // 토큰을 가지고 풀기
//                .getBody();
//        return claims.getSubject();
//    }
//        // 점검해서 꺼내오기
//}
