package com.smartChart.smsMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.smartChart.smsMessage.dto.RecipientInfoRequest;
import com.smartChart.smsMessage.dto.SmsResponse;
import com.smartChart.smsMessage.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
public class SmsController {
    private final SmsService smsService;

    @PostMapping("/doctor/reservation-text")
    public ResponseEntity<SmsResponse> test(@RequestBody RecipientInfoRequest request) throws NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, InvalidKeyException, JsonProcessingException {
        SmsResponse data = smsService.sendSms(request.getReservationId(), request.getRecipientPhoneNumber(), request.getContent());
        return ResponseEntity.ok().body(data);
    }
}
