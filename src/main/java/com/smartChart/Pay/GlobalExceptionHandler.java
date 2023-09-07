package com.smartChart.Pay;
//
//import com.siot.IamportRestClient.exception.IamportResponseException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestController;
//
//@ControllerAdvice
//@RestController
//public class GlobalExceptionHandler {
//
//
//    /**
//     * 결제관련 예외
//     * @return
//     */
//    @ExceptionHandler(IamportResponseException.class)
//    public ResponseEntity<String> IamportResponseException(){
//        return new ResponseEntity<>("결제관련해서 에러가 발생",HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    /**
//     * 그외 모든 에러는 서버에러로~
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(Exception.class) // @ExceptionHandler같은 경우는 @Controller, @RestController가 적용된 Bean내에서 발생하는 예외를 잡아서 하나의 메서드에서 처리해주는 기능을 한다.
//    public ResponseEntity<String> handleArgumentException(Exception e) {
//        String message = "결제관련 에러가 발생했습니다. 실제금액과 맞는지 확인 후, 관리자에게 문의해주세요.";
//        return new ResponseEntity<String>(message, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//
//
//}