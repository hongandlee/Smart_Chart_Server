package com.smartChart.naver;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // @Getter / @Setter, @ToString, @EqualsAndHashCode와 @RequiredArgsConstructor, @Value 를 합쳐놓은 종합 선물 세트
@Builder  // 객체를 생성할 수 있는 빌더를 builder() 함수를 통해 얻고 거기에 셋팅하고자 하는 값을 셋팅하고 마지막에 build()를 통해 빌더를 작동 시켜 객체를 생성
@AllArgsConstructor
@NoArgsConstructor
public class naverRequest {

    private String query;  // 검색을 원하는 문자열로서 UTF-8로 인코딩한다.

    private int display = 10;  // 검색 결과 출력 건수 지정(1 ~ 5)

    private int  start = 1;  // 검색 시작 위치로 1만 가능

    private String sort = "random";  // 정렬 옵션: random(유사도순), comment(카페/블로그 리뷰 개수 순)
}
