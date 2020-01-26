# 사전과제 3. 주택 금융 서비스 API 개발
 
## 개발 프레임워크 
* Java8
* Spring boot
* Spring data jpa
* H2
* lombok
* opencsv
* Java JWT
* apache commons-lang3
* maven

## 문제해결 전략
### DB 테이블
<img src="./img/task3-db.jpg" title="디비 구조">

### 프로젝트 패키지 구조
<img src="./img/task3_package.jpg" title="패키지 구조">

### api별 문제해결 전략
#### 1. 데이터 파일에서 각 레코드를 데이터베이스에 저장하는 API 
  * multipart content-type 타입으로 csv 파일을 받는다.
  * opencsv를 이용하여 파싱하여 데이터를 추출 한다.
  * 첫라인(헤더)에서 기관(은행)정보를 추출하여 금융기관(institution) 테이블 저장 한다.
  * 두번째라인부터 마지막라인까지 추출 하여 연도, 월, 기관, 금액 정보를 주택금융기금(house_fund) 테이블 저장 한다.
  * 결과값으로 추가된 금융기관과 주택금융기금 개수를 결과로 내려 준다.

#### 2. 주택금융 공급 금융기관(은행) 목록을 출력하는 API 
  * 디비 금융기관(institution) 테이블 저장된 모든 데이터를 검색하여 내려 준다.

#### 3. 년도별 각 금융기관의 지원금액 합계를 출력하는 API 
  * 디비 주택금융기금(house_fund) 테이블에서 년도와 기관별로 group by 하여 금액 합산 결과를 쿼리로 가져온다.
  * 년도별 총 금액과 각 금융기관의 금액 합계 결과를 내려 준다.

#### 4. 각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 출력하는 API
  * 디비 주택금융기금(house_fund) 테이블에서 년도와 기관별로 group by 하여 금액 합산 결과를 쿼리로 가져온다.
  * 합산된 금액 기준으로 sort 하여 제일 큰 금액을 찾아 결과를 내려 준다.

#### 5. 전체 년도에서 외환은행의 지원금액 평균 중에서 가장 작은 금액과 큰 금액을 출력하는 API
  * 디비 금융기관(institution)테이블에서 기관 정보를 가져온다.
  * 디비 주택금융기금(house_fund)테이블에서 해당 기관의 년도별 group by 하여 금액 평균값을 쿼리로 가져온다.
  * 디비에서 가져온 결과에서 가장 작은 값괄 큰 값을 찾아 결과를 내려 준다.

#### 6. 특정 은행의 특정 달에 대해서 2018년도 해당 달에 금융지원 금액을 예측하는 API
  * 디비 금융기관(institution)테이블에서 기관 정보를 가져온다.
  * 디비 주택금융기금(house_fund)테이블에서 해당 기관의 모든 정보를 가져온다.

#### 7. 계정생성 API
  * id와 패스워드를 받아 패스워드는 sha256으로 인코딩 한다.
  * 계정 정보를 디비에 저장 한다.
  * 계정 정보로 jwt를 만든다. 
  * jwt는 jjwt를 사용하여 만듬(https://github.com/jwtk/jjwt)
  * id와 생성된 토큰정보를 내려 준다.

#### 8. 로그인 API
  * id와 패스워드를 받아 id로 디비에서 계정정보를 검색한다.
  * 검색된 계정정보에서 패스워드 검증을 한다.
  * 검증이 성공하면 토큰을 생성하연 내려 준다.

#### 9. 토큰 검증 및 재발급
  * 검증과 재발급 처리는 spring interceptor를 사용하여 처리 한다.
  * 모든 http Authorization 헤더에 토큰이 있는지 체크 한다.
  * 토큰이 있다면 검증 한다. 검증에 성공하면 api에 접근 할수 있다.
  * 토큰이 없거나 검증 실패 하면 api에 접근 할수 없다.
  * Authorization 헤더에 "Bearer_Token" 값이 들어 있다면 기존 토큰을 검증하고 토큰을 재발행 한다.


## 빌드 및 실행 방법
  * Java, maven은 설치되어 있어야 합니다.
  * 프록젝트 maven으로 빌드(프로젝트 디렉토리에)
  ```bash
  $ mvn install
  ```
  * 빌드 후 java -jar 으로 실행 방법 
  ```bash
  $ java -jar target/house-0.0.1-SNAPSHOT.jar
  ```
  * maven으로 spring boot 실행 방법
  ```bash
  $ mvn spring-boot:run
  ```
 

## API 목록



