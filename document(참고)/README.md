# GraduationProject
한국산업기술대학교 졸업작품



웹서버 참고 사항

AWS 루트 주소 : http://13.209.3.5:8080
루트 주소 뒤에 세부 URL을 붙여 사용
통신 오류 시 일괄적으로 503 service unavailable ERROR를 전송

참고 :로그인 후 userID 정보는 세션으로 관리되므로, 서버 재시작 등의 이유로 세션 유지가 안 될 경우 오류가 발생할 수 있음. 오류가 자꾸 발생할 때는 재로그인 바람



I. 회원 관리
공통 URL : /user

1. 회원 정보 조회 (수정중)
/user/myInfo
1.1. Request 
● 메소드 : GET
● 세션 정보로 userID가 관리되므로 파라미터 전송할 필요 없음.
1.2. Response
● Json 형식의 회원 정보
● 예 {"id":"candy","name":"lemon","mail":"lemon@gmail.com","pwd":"5577","gender":"male","age":"5000","emailChecked":"no"}

2. 회원 가입
/user/join 
2.1. Request 
● 메소드 : POST
● 컨텐츠 타입 : application/json
● Json key값 : id, name, mail, pwd, gender, age, emailChecked
2.2. Response
● 중복된 아이디 – id
● 회원가입 성공 – ok
● 회원가입 실패 – fail

3. 로그인
/user/login 
3.1. Request 
● 메소드 : POST
● 컨텐츠 타입 : application/json
● Json key값 : id, pwd
3.2. Response
● 로그인 성공 – true
● 로그인 실패 – false

4. 회원 정보 수정
/user/modify
4.1. Request 
● 메소드 : PUT
● 컨텐츠 타입 : application/json
● Json key값 : id, name, mail, pwd, gender, age, emailChecked
4.2. Response
● 성공 – ok
● 실패 – fail

5. 회원 정보 삭제
user/delete/{id}
5.1. Request 
● 메소드 : DELETE
● {id} 부분에 사용자의 id를 넣은 주소로 요청
5.2. Response
● 성공 – ok
● 실패 – fail


II. 옷 관리
공통 URL : /clothes
모든 회원은 기본적으로 ‘default’라는 이름의 옷장을 가지므로 테스트 시 이용 바람.
모든 옷의 식별자는 no이다.

6. 옷 정보 조회
/clothes/info/{no}
6.1. Request
● 메소드 : GET
● {no} 부분에 옷의 고유 no를 넣은 주소로 요청
6.2. Response
● Json 형식의 옷 정보 (안드로이드 retrofit에 의해 자동으로 내부의 VO로 변환)
● 예 {"no":60,"name":"brown skirt","category":"skirt","brand":"zara","color":"brown","date":"200430","season":"spring","cloSize":"s","img":null,"fileName":"myTemp993466093.tmp","originFileName":null,"filePath":"/download/windows?imageFileName=myTemp993466093.tmp","like":null,"userID":"air","closetName":"default","pageStart":-1,"pageSize":-1}

7. 해당 사용자의 모든 옷 조회
/clothes/all
7.1. Request
● 메소드 : GET
● (옵션) 파라미터 : page(조회할 페이지 수), pageSize(페이지 당 조회할 자료 개수)
7.2. Response
● Json Array 형식의 옷 정보 리스트 (안드로이드 retrofit에 의해 자동으로 내부의 VO 리스트로 변환)

8. 옷 검색
/clothes/search
8.1. Request
● 메소드 : GET
● 파라미터 : 다음 중 검색할 조건의 파라미터 (중복 가능)name, category, brand, color, date, season, cloSize, fileName, like, closetName
● (옵션) 파라미터 : page(조회할 페이지 수), pageSize(페이지 당 조회할 자료 개수)
8.2. Response
● Json Array 형식의 옷 정보 리스트

9. 옷 추가
/clothes/add
9.1. Request
● 메소드 : POST
● 컨텐츠 타입 : multipart/form-data
● 같이 전달할 파라미터 : name, category, brand, color, date, season, cloSize, fileName, closetName
● 누락하면 안 되는 파라미터 : closetName (해당하는 옷장 정보가 없을 경우 추가되지 않음)
9.2. Response
● 성공 – ok
● 실패 – fail

10. 옷 삭제
/clothes/delete/{no}
10.1. Request 
● 메소드 : DELETE
● {no} 부분에 옷의 고유 번호를 넣은 주소로 요청
10.2. Response
● 성공 – ok
● 실패 – fail

(이후 추가)
III. 코디 관리


공통 URL : /codi
모든 코디의 식별자는 codiNo이다.

11. 코디 정보 조회
/codi/info/{codiNo}
11.1. Request
● 메소드 : GET
● {codiNo} 부분에 코디의 고유 식별자인 codiNo를 넣은 주소로 요청
11.2. Response
● Json 형식의 옷 정보 (안드로이드 retrofit에 의해 자동으로 내부의 VO로 변환)
● 예 {}

12. 해당 사용자의 모든 코디 조회
/codi/all
12.1. Request
● 메소드 : GET
● (옵션) 파라미터 : page(조회할 페이지 수), pageSize(페이지 당 조회할 자료 개수)
12.2. Response
● Json Array 형식의 옷 정보 리스트 (안드로이드 retrofit에 의해 자동으로 내부의 VO 리스트로 변환)

13. 코디 검색
/codi/search
13.1. Request
● 메소드 : GET
● 파라미터 : 다음 중 검색할 조건의 파라미터 (중복 가능)codiNo, codiName, season, where, buyDate, comment, like, fileName, filePath, addedDate
● (옵션) 파라미터 : page(조회할 페이지 수), pageSize(페이지 당 조회할 자료 개수)
13.2. Response
● Json Array 형식의 옷 정보 리스트

14. 코디 추가
/codi/add
14.1. Request
● 메소드 : POST
● 컨텐츠 타입 : multipart/form-data (이미지)
● 같이 전달할 파라미터 : season, where
14.2. Response
● 성공 – ok
● 실패 – fail

15. 코디 삭제
/codi/delete/{codiNo}
15.1. Request
● 메소드 : DELETE
● {codiNo} 부분에 코디의 고유 식별자인 codiNo를 넣은 주소로 요청
15.2. Response
● 성공 – ok
● 실패 – fail

코디 DB 정보:
 `codiNo`           INT             NOT NULL    COMMENT '코디번호(대리키)' AUTO_INCREMENT, 
    `codiName`         VARCHAR(45)     NULL        COMMENT '이름', 
    `season`     VARCHAR(45)     NULL        COMMENT '계절', 
    `where`        VARCHAR(45)     NULL        COMMENT '장소', 
    `buyDate`        DATE     NULL        COMMENT '구입 날짜', 
    `comment`         VARCHAR(1000)     NULL        COMMENT '코멘트 500자', 
    `like`       VARCHAR(10)     NULL		DEFAULT 'no'        COMMENT '즐겨찾기 여부', 
    `fileName`         VARCHAR(50)     NULL        COMMENT '파일이름', 
    `filePath`          VARCHAR(80)     NULL        COMMENT '파일경로', 
    `addedDate`        TIMESTAMP     NOT NULL	DEFAULT CURRENT_TIMESTAMP        COMMENT '추가 날짜', 
    `userID`          VARCHAR(30)     NOT NULL    COMMENT '유저아이디', 
    PRIMARY KEY (codiNo)

=>외래키 :userID
