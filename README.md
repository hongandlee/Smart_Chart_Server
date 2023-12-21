# ✨ Smart_Chart_Server

- 환자와 의사를 위해 병원에서 빠른 일처리를 돕는 smart_chart 서비스 입니다.
- 환자는 병원 예약, 진료비 내기, 기본 건강체크, 실시간 진료상담을 이용할 수 있습니다.
- 의사는 예약/진료 관리, 환자 대기 관리, 매출관리, 실시간 진료상담을 이용할 수 있습니다.
<br><br>


        Smart_Chart 링크 : https://smartchart.vercel.app/ <br>
        의사  - ID: doctor@gmail.com     / password : doctor <br>
        환자 - ID : patient@gmail.com     / password : patient 

<br><br><br><br>

## Description
**역할**

- 백엔드 : 홍유리 (기여도 100%)
- 프론트엔드 : 홍왕렬, 이정민 
- API 설계 : 홍유리 (기여도 100%)
- DB 설계 : 홍유리 (기여도 100%)
- 콘텐츠 기획 및 제작 : 홍유리 (기여도 90%) , 홍왕렬, 이정민
<br>

**📆 기간**

- 2023.09 ~ 2023.12
<br><br><br><br>

## **🔍핵심 기능**

- Chart.js로 병원 매출관리 조회 기능 (월별, 년도별, 일별, 주간별, 기간별, 나이별, 성별, 최신순, 매출순)
- Websocket을 이용한 실시간 진료 상담을 위한 실시간 채팅 기능
- i’mort, KakaoPay를 이용한 치료비 결제 기능
- 네이버 지역정보 검색 기능
- Gamil SMTP로 예약 확정 체크 문자 서비스
- 오늘의 환자 대기 관리 기능
- kakaomap 병원 검색 및 예약
- 환자 기본 건강체크 서비스
- 의사 진료 관리 서비스
- 의사 예약 관리 서비스
<br><br><br><br>


## **담당업무**

1. Github Actions, S3, Code Deploy를 이용한 CI / CD 구축
    
    [DevOps - CI/CD 구축하기 #1 ( Github Actions, S3, Code Deploy, EC2)](https://dbfl720.tistory.com/866)
    
    [DevOps - CI/CD 구축하기 #2 ( Github Actions, S3, Code Deploy, EC2)](https://dbfl720.tistory.com/867)
    <br><br>

2. Nginx로 HTTPS 개발 및 리버스 프록시 서버 세팅
    - Cerbot 설치 및 Let’s Encrypt에서 SSL 인증서 발급
    - Crontab으로 SSL 인증서 자동 갱신 설정
    - Nginx로 HTTPS 구축 시 발생한 이슈 해결
        - Nginx와 같은 포트 번호사용을 인한 무한루프 발생으로 다른 포트 적용(8080)으로 해결
        
      [Let's Encrypt와 Nginx로 HTTPS 만들기](https://dbfl720.tistory.com/878)
      <br><br>  

3. 병원 매출관리를 위한  다양한 조회 로직 구현
    - 월별, 년도별, 일별, 주간별, 기간별, 나이별, 성별, 최신순, 매출순
    - 월별 성별에 따른 조회 JPA
    
      [월별 성별에 따른 조회 JPA - nativeQuery](https://dbfl720.tistory.com/849)
     <br><br>
     
4. Spring Security를 이용한 개발 및 이슈처리
    - 403 Forbidden 응답이 반환되는 문제를 별도의 엔트리포인트를 작성하여 스프링 시큐리티에 등록으로 해결 <br>
    
      [403 Forbidden 응답이 반환 With Spring Security](https://dbfl720.tistory.com/858)
  

<br><br><br><br>

## 개발 환경

**Backend**

- Java / Spring Boot / Spring Security / JWT / WebSocket / SocketJS / Stomp / JSP / JPA

**Database**

- MySQL

**Server**

- Amazon S3 / Amazon EC2 / Amazon RDS / GitHub Actions / Amazon CodeDeploy / Apache Tomcat / NGINX

**OS**

- Ubuntu

**Tools**

- Github / Postman / IntelliJ IDEA / Discord / Figma


<br><br><br><br>
## Project Proposal
- 블로그 : https://dbfl720.tistory.com
- DB 설계 사이트 : https://docs.google.com/spreadsheets/d/1XeGVaTzEo_Z2p7Sk135XaQQoKZsWAnsB9j8veAh58tQ/edit#gid=0
- 환자 API 명세서 사이트 : https://docs.google.com/spreadsheets/d/1XeGVaTzEo_Z2p7Sk135XaQQoKZsWAnsB9j8veAh58tQ/edit#gid=1565076207
- 의사 API 명세서 사이트 : https://docs.google.com/spreadsheets/d/1XeGVaTzEo_Z2p7Sk135XaQQoKZsWAnsB9j8veAh58tQ/edit#gid=368826546
- 결제, Jwt 시퀀스 다이어그램 : https://drive.google.com/file/d/1IQdlAdb0iBbZVr_KZd2akMV8AP3Cokz9/view?usp=sharing
- 기획 Figma 사이트 : https://www.figma.com/file/s4tvcCbhoY5xAF6ZQFh2Gd/%EC%8A%A4%EB%A7%88%ED%8A%B8%EC%B0%A8%ED%8A%B8?type=design&node-id=0-1&mode=design&t=cctDsTWrkLHSc4if-0


<br><br><br><br>


## Service Architecture
![Service Architecture](https://github.com/hongandlee/Smart_Chart_Server/assets/116433637/67f82385-7118-4ba4-a37c-161988d45a16)


<br><br><br><br>
## Database
![스크린샷 2023-11-20 오후 10 48 08](https://github.com/hongandlee/Smart_Chart_Server/assets/116433637/44af57d3-3cab-4e2d-937f-aac4b21d4fcf)

<br><br><br><br>
## 결제, JWT Sequence Diagram
<img width="775" alt="스크린샷 2023-09-15 오후 8 22 23" src="https://github.com/hongandlee/Smart_Chart_Server/assets/116433637/e3a9c60e-09ad-492b-8cb0-0fcc1c561d45">
<br><br><br><br>
<img width="768" alt="스크린샷 2023-09-15 오후 8 20 36" src="https://github.com/hongandlee/Smart_Chart_Server/assets/116433637/6ed052f0-a076-419b-9d24-cf39147bec29">
