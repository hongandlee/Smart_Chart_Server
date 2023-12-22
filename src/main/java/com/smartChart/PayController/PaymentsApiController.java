package com.smartChart.PayController;


import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.smartChart.Pay.PaymentService;
import com.smartChart.Response.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PaymentsApiController {


    @Autowired
    private PaymentService paymentService;

    //토큰 발급을 위해 아임포트에서 제공해주는 rest api 사용.(gradle로 의존성 추가)
    private final IamportClient iamportClientApi;



    @Value("${apiKey.key}")
    private String apiKey;



    @Value("${apiSecret.key}")
    private String apiSecret;





    //생성자로 rest api key와 secret을 입력해서 토큰 바로생성.
    public PaymentsApiController() {
        this.iamportClientApi = new IamportClient("0605446434620106",
                "W8JPxGzHas0IClss49iimjvVog91oRmH1rZbsSYPak4xiazXKIRzq1aBXkGMheCTYDfD1X5nwT6PMw5X");
    }



    /**
     * impUid로 결제내역 조회.
     * @param impUid
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException {
        return iamportClientApi.paymentByImpUid(impUid);
    }



    /**
     * 결제검증을 위한 메서드
     * map에는 imp_uid, amount, reservationId 이 키값으로 넘어옴.
     * @param map
     * @return
     * @throws IamportResponseException
     * @throws IOException
     */
    @PostMapping("/vertifyIamport")
    public ResponseEntity<Message> verifyIamport(@RequestBody Map<String,String> map) throws IamportResponseException, IOException{
        String impUid = map.get("imp_uid");//실제 결제금액 조회위한 아임포트 서버쪽에서 id
        int reservationId = Integer.parseInt(map.get("reservationId")); //DB에서 예약 가격 조회를 위한 번호
        int amount = Integer.parseInt(map.get("amount"));//실제로 유저가 결제한 금액

        //아임포트 서버쪽에 결제된 정보 조회.
        //paymentByImpUid 는 아임포트에 제공해주는 api인 결제내역 조회(/payments/{imp_uid})의 역할을 함.
        try {
            IamportResponse<Payment> irsp = paymentLookup(impUid);
            paymentService.verifyIamportService(irsp, amount, reservationId);

            Message successMessage = new Message();
            successMessage.setCode(200);
            successMessage.setMessage("성공");

            return ResponseEntity.ok(successMessage);
        } catch (RuntimeException e) {
            Message errorMessage = new Message();
            errorMessage.setCode(500);
            errorMessage.setMessage("실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorMessage);
        }
    }


}
