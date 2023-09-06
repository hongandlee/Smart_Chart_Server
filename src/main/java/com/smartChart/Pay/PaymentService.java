package com.smartChart.Pay;


import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import com.smartChart.cost.service.TreatmentStatementService;
import com.smartChart.reservation.entity.Reservation;
import com.smartChart.reservation.repository.ReservationRepository;
import com.smartChart.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TreatmentStatementService treatmentStatementService;

    private final ReservationService reservationService;

    private final ReservationRepository reservationRepository;




    /**
     * 아임포트 서버쪽 결제내역과 DB에 물건가격을 비교하는 서비스.
     * 다름 -> 예외 발생시키고 GlobalExceptionHandler쪽에서 예외처리
     * 같음 -> 결제정보를 DB에 저장(PaymentsInfo 테이블)
     * @param irsp (아임포트쪽 결제 내역 조회 정보)
     * @param reservationId (내 DB에서 물건가격 알기위한 경매게시글 번호)
     */
    @Transactional
    public void verifyIamportService(IamportResponse<Payment> irsp, int amount, int reservationId) {
         Reservation reservation = reservationService.findById(reservationId); // 예약 정보
         int sum = treatmentStatementService.selectByReservationId(reservationId); // sum 정보


        //실제로 결제된 금액과 아임포트 서버쪽 결제내역 금액과 같은지 확인
        //이때 가격은 BigDecimal이란 데이터 타입으로 주로 금융쪽에서 정확한 값표현을 위해씀.
        //int형으로 비교해주기 위해 형변환 필요.

        if(irsp.getResponse().getAmount().intValue()!=amount) {
            log.info("##########결제된 금액과 아임포트 서버 내역의 금액이 다릅니다.");
            throw new RuntimeException("결제된 금액과 아임포트 서버 내역의 금액이 다릅니다.");
        } else if (amount!=sum){
            log.info("##########결제된 금액과 Database 서버 내역의 금액이 다릅니다.");
            throw new RuntimeException("결제된 금액과 Database 서버 내역의 금액이 다릅니다."); //DB에서 물건가격과 실제 결제금액이 일치하는지 확인하기. 만약 다르면 예외 발생시키기.!
        } else {
            //아임포트에서 서버쪽 결제내역과 DB의 결제 내역 금액이 같으면 DB에 결제 정보를 삽입.
            reservation = reservation.toBuilder()
                    .amount(amount)
                    .build();
            reservationRepository.save(reservation);
        }

    }





}
