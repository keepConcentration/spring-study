# HTTP 메서드 활용

- 클라이언트에서 서버로 데이터 전송
- HTTP API 설계 예시

## 클라이언트에서 서버로 데이터 전송

### 데이터 전달방식은 크게 2가지

- **쿼리 파라미터를 통한 데이터 전송**
  - GET
  - 주로 정렬 필터(검색어)

- **메세지 바디를 통한 데이터 전송**
  - POST, PUT, PATCH
  - 회원 가입, 상품 주문, 리소스 등록, 리소스 변경

### 4가지 상황

- **정적 데이터 조회**
  - 이미지, 정적 테스트 문서
  - 조회는 GET 사용
  - 정적 데이터는 일반적으로 쿼리 파라미터 없이 리소스 경로로 단순하게 조회 가능
- **동적 데이터 조회**
  - 주로 검색, 게시판 목록에서 정렬 필터(검색어)
  - 조회 조건을 줄여주는 필터, 조회 결과를 정렬하는 정렬 조건에 주로 사용
  - GET은 쿼리 파라미터 사용해서 데이터를 전달
  - 조회는 GET 사용
- **HTML Form을 통한 데이터 전송**
  - 회원 가입, 상품 주문, 데이터 변경
  - `Content-Type: application/x-www-form-urlencoded`
  - form의 내용을 메시지 바디를 통해서 전송
  - 전송 데이터를 url encoding처리
    - ex) abc김 -> abc%EA%B9%80
  - GET 전송 가능
  - 파일 전송 시 `Content-Type: multipart/form-data; boundary=----XXX`
  - HTML Form 전송은 **GET, POST만 지원**
- **HTTP API를 통한 데이터 전송**
  - 회원 가입, 상품 주문, 데이터 변경
  - 서버 to 서버
    - 백엔드 시스템 통신
  - 앱 클라이언트
    - 아이폰, 안드로이드
  - 웹 클라이언트
    - HTML 에서 Form 전송 대신 자바스크립트를 통한 통신에 사용(Ajax)
  - POST, PUT, PATCH: 메시지 바디를 통해 데이터 전송
  - GET: 조회, 쿼리 파라미터로 데이터 전달
  - Content-Type: application/json을 주로 사용(사실상 표준)
    - TEXT, XML, JSON 등등

## HTTP API 설계 예시

- **HTTP API 컬렉션**
  - **POST 기반 등록**
  - ex) 회원 관리 API 제공
- **HTTP API - 스토어**
  - **PUT 기반 등록**
  - ex) 정적 컨텐츠 관리, 원격 파일 관리
- **HTTP FORM 사용**
  - 웹 페이지 회원 관리
  - GET, POST만 지원

### 회원 관리 시스템

#### API 설계 - POST 기반 등록

- **회원** 목록 /members **-> GET**
- **회원** 등록 /members **-> POST**
- **회원** 조회 /members/{id} **-> GET**
- **회원** 수정 /members/{id} **-> PATCH, PUT, POST**
- **회원** 삭제 /members/{id} **-> DELETE**

#### POST - 신규 자원 특징

- 클라이언트는 등록될 리소스의 URI를 모른다.
  - 회원등록 /members -> POST
  - POST /members
- 서버가 새로 등록된 리소스 URI를 생성해준다.
  - HTTP/1.1 201 Created
  - Location: **/members/100**

- 컬렉션(Collection)
  - 서버가 관리하는 리소스 디렉토리
  - 서버가 리소스의 URI를 생성하고 관리
  - 여기서 컬렉션은 /members

### 파일 관리 시스템

#### API 설계 - PUT 기반 등록

- **파일** 목록 /files **-> GET**
- **파일** 조회 /files/{filename} **-> GET**
- **파일** 등록 /files/{filename}  **-> PUT**
- **파일** 삭제 /files/{filename} **-> DELETE**
- **파일** 대량 등록 /files **-> POST**

#### PUT 신규 자원 등록 특징

- 클라이언트가 리소스 URI를 알고 있어야한다.
  - 파일 등록 /files{filename} -> PUT
  - PUT **/file/star.jpg**
- 클라이언트가 직접 리소스의 URI를 지정한다.
- 스토어(Store)
  - 클라이언트가 관리하는 리소스 저장소
  - 클라이언트가 리소스의 URI를 알고 관리
  - 여기서 스토어는 /files

### HTML FORM 사용

- HTML FORM은 **GET, POST만 지원**
- AJAX 같은 기술을 사용해서 해결 가능 -> 회원 API 참고
- 여기서는 순수 HTML, HTML FORM 이야기
- GET, POST만 지원하므로 제약이 있음

  - **회원** 목록   /members **-> GET**
  - **회원** 등록 폼 /members/new **-> GET**
  - **회원** 등록   /members/new, /members **-> POST**
  - **회원** 조회   /members/{id} **-> GET**
  - **회원** 수정 폼 /members/{id}/edit **-> GET**
  - **회원** 수정   /members/{id}/edit, /members/{id} **-> POST**
  - **회원** 삭제   /members/{id}/delete **-> POST**

- **컨트롤 URI**
  - GET, POST만 지원하므로 제약이 있음
  - 이런 제약을 해결하기 위해 동사로 된 리소스 경로 사용
  - POST의 /new, /edit, /delete가 컨트롤 URI
  - HTTP 메서드로 해결하기 애매한 경우 사용(HTTP API 포함)


### 정리

#### 참고하면 좋은 URI 설계 개념

- **문서(document)**
  - 단일 개념(파일 하나, 객체 인스턴스, 데이터베이스 row)
  - ex) members/100, files/star.jpg
- **컬렉션(collection)**
  - 서버가 관리하는 리소스 디렉터리
  - 서버가 리소스의 URI를 생성하고 관리
  - ex) /members
- **스토어(store)**
  - 클라이언트가 관리하는 자원 저장소
  - 클라이언트가 리소스의 URI를 알고 관리
  - ex) files
- **컨트롤러(controller), 컨트롤 URI**
  - 문서, 컬렉션, 스토어로 해결하기 어려운 추가 프로세스 실행
  - 동사를 직접 사용
  - ex) /members/{id}/delete

> https://restfulapi.net/resource-naming