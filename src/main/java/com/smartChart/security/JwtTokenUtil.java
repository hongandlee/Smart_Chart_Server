//package com.smartChart.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.mybatis.logging.Logger;
//import org.mybatis.logging.LoggerFactory;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;
//import java.security.Key;
//import java.util.Date;
//
//
//@Service
//public class JwtTokenUtil {
//    private static final String SECRET_KEY = "XUSMhedg8DLHCk/ArcQGfGpQfYrBMpifzjUe1nq+D3tm4WVgXrJl2pxyVPccZDosjqZA8ZVvNb4dAD6evkfrFVVh5/b2PQ54GCbrrzoBuccbAMsz63ASB88C9d4PzpdGs2xsbpUvh/ODF2WmRvHxwtZde55NDHfn/q5MJ32vAQQdCJam1Zez2dTXjEo9qta1m21m4+i/dpyzZZcKqzN/T64vVDzzysqXxuRaZ4J94RnvveLe/cFpL3hG8KZMTtdNEjL0dfsyo93tDV0pZswI5LsHjig1pHJzwnuYtZZ36WpF5dd14UIA2qIIai0YJlfv3V/hl5hqrfro3aMBCx+8BMJvUz8HxRIKnstQfWgvjhY=\\n";  // 이렇게 하면 x
//    // 유저의 비밀번호를 이용해서 만들 수 있다. ?
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//
//    /**
//     * JWT - 토큰 만들기
//     * @param subject
//     * @return
//     */
//    public String createToken(String subject) { // subject: user 아이디
//
//        var expTime = 2*1000*60;
//
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
//                .setExpiration(new Date(System.currentTimeMillis() + (2*1000*60))) // 만료 시간 설정
//                .compact();
//
//    }
//
//
//    /**
//     * token 으로부터 claim 정보 조회
//     * @param token
//     * @return
//     */
//    public Claims getSubject(String token) {
//        Claims claims = Jwts.parserBuilder()  // claims : payload에 담길 정보
//                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
//                .build()
//                .parseClaimsJws(token) // 토큰을 가지고 풀기
//                .getBody();
//
//        return claims;
//    }






//}
