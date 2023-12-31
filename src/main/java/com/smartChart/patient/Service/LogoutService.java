package com.smartChart.patient.Service;

import com.smartChart.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {
        // 토큰 제거
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        jwt = authHeader.substring(7); // Bearer 앞 공간이 7자라서..
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }


        // 세션 제거
        HttpSession session = request.getSession(false); // 이미 해당 요청과 연결된 세션이 존재하는 경우, 해당 세션 객체를 반환합니다.
        if (session != null) {
            session.removeAttribute("patientId");
            session.removeAttribute("patientEmail");
            session.removeAttribute("patientName");
        }
    }
}
