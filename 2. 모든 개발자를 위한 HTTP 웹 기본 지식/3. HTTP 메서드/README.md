# HTTP 메서드
- HTTP API를 만들어보자
- HTTP 메서드 - GET, POST
- HTTP 메서드 - PUT, PATCH, DELETE
- HTTP 메서드의 속성

## HTTP API를 만들어보자

### 요구사항

#### 회원 정보 관리 API를 만들어라.
- 회원 목록 조회
- 회원 조회
- 회원 등록
- 회원 수정
- 회원 삭제

#### API URI 설계
- 회원 목록 조회 /read-memmber-list
- 회원 조회 /read-member-by-id
- 회원 등록 /create-member
- 회원 수정 /update-member
- 회원 삭제 /delete-member

> 그럴듯하게 URI가 설계가 되었다 하지만 이것은 좋은 URI 설계일까?
가장 중요한 것은 리소스가 식별이 되어야한다.

### API URI 고민

- 리소스의 의미는 뭘까?
  - 회원들 등록하고 수정하고 조회하는게 리소스가 아니다.
  - ex) 미네랄을 캐라 -> 미네랄이 리소스
  - **회원이라는 개념 자체가 바로 리소스다.**
- 리소스를 어떻게 식별하는게 좋을까?
  - 회원을 등록하고 수정하고 조회하는 것을 모두 배제
  - **회원이라는 리소스만 식별하면 된다. -> 회원 리소스를 URI에 매핑

### API URI 설계
#### 계층형 구조 횔용
- **회원** 목록 조회 /members
- **회원** 조회 /members/{id}
- **회원** 등록 /members/{id}
- **회원** 수정 /members/{id}
- **회원** 삭제 /members/{id}

### 리소스와 행위를 분리

#### 가장 중요한 것은 리소스를 식별하는 것
- **URI는 리소스만 식별한다.**
- **리소스**와 해당 리소스를 대상으로 하는 **행위**를 분리
  - 리소스: 회원
  - 행위: 조회, 등록, 수정, 삭제
- 리소스는 명사(미네랄), 행위는 동사(캐라)
- 행위(메서드)는 어떻게 구분 ?

## HTTP 메서드 - GET, POST

 클라이언트가 요청할 때 기대하는 행위

### HTTP 메서드 종류
#### 주요 메서드
*Representation 설명 전*
- GET: 리소스 조회
- POST: 요청 데이터 처리, 주로 등록에 사용
- PUT: 리소스를 대체, 해당 리소스가 없으면 생성(MERGE, PASTE)
- PATCH: 리소스 부분 변경
- DELETE: 리소스 삭제

#### 기타 메서드
- **HEAD**: GET과 동일하지만 메시지 부분을 제외하고, 상태 줄과 헤더만 반환
- **OPTIONS**: 대상 리소스에 대한 통신 가능 옵션(메서드)을 설명(주로 CORS에서 사용)
- **CONNECT**: 대상 자원으로 식별되는 서버에 대한 터널을 설정 - 거의 사용하지 않음
- **TRACE**: 대상 리소스에 대한 경로를 따라 메시지 루프백 테스트를 수행 - 거의 사용하지 않음

### GET
```
GET /members/100 HTTP/1.1
Host: localhost:8080

```
- 리소스 조회
- 서버에 전달하고 싶은 데이터는 query(쿼리 파라미터, 쿼리 스트링)를 통해서 전달
- 최근 스펙에선 메시지 바디를 사용해서 데이터를 전달할 수도 있지만, 지원하지 않는 서버가 많아서 권장하지 않음.

#### 리소스 조회

- 클라이언트 요청 메세지(request)
```
GET /members/100 HTTP/1.1
HOST: localhost:8080

```

- 서버에서 응답 메세지 생성
```
{
  "username": "young",
  "age": 20
}
```

- 응답 메세지(response)
```
HTTP/1.1 200 OK
Content-Type: application/json
Content-Length: 34

{
  "username": "young",
  "age": 20
}
```

### POST
```
POST /members HTTP/1.1
Content-Type: application/json

{
  "username": "hello",
  "age": 20
}
```
- 요청 데이터 처리
- **메시지 바디를 통해 서버로 요청 데이터 전달**
- 서버는 요청 데이터를 **처리**
  - 메시지 바디를 통해 들어온 데이터를 처리하는 모든 기능을 수행한다.
- 주로 전달된 데이터로 신규 리소스 등록, 프로세스 처리에 사용

#### 리소스 등록

- 클라이언트 요청 메세지(request)
```
POST /members HTTP/1.1
Content-Type: application/json

{
  "username": "hello",
  "age": 20
}
```

- 서버에서 응답 메세지 생성
```
{
  "username": "hello",
  "age": 20
}
```

- 응답 메세지(response)
```
HTTP/1.1 201 Created
Content-Type: application/json
Content-Length: 34
Location: /members/100

{
  "username": "hello",
  "age": 20
}
```

#### POST는 요청 데이터를 어떻게 처리한다는 뜻일까?
- 스펙: POST 메서드는 **대상 리소스가 리소스의 고유한 의미 체계에 따라 요청에 포함된 표헌을 처리하도록 요청**합니다. (구글 번역)
- 예를 들어 POST는 다음과 같은 기능에 사용된다.
  - HTML 양식에 입력된 필드와 같은 데이터 블록을 데이터 처리 프로세스에 제공
    - ex) HTML form에 입력한 정보로 회원 가입, 주문 등에서 사용
  - 게시판, 뉴스 그룹, 메일링 리스트, 블로그 또는 유사한 기사 그룹에 메시지 게시
    - ex) 게시판 글쓰기, 댓글 달기
  - 서버가 아직 식별하지 않은 새 리소스 생성
    - ex) 신규 주문 생성
  - 기존 자원에 데이터 추가
    - ex) 한 문서 끝에 내용 추가하기

- **이 리소스 URI에 POST요청이 오면 요청 데이터를 어떻게 처리할 지 리소스 별로 따로 정해야함 -> 정해진 것이 없다.**

#### POST 정리

- **1. 새 리소스 생성(등록)**
  - 서버가 아직 식별하지 않은 새 리소스 생성
- **2. 요청 데이터 처리**
  - 단순히 데이터를 생성하거나, 변경하는 것을 넘어서 프로세스를 처리해야 하는 경우
  - ex) 주문에서 결제 완료 -> 배달 시작 -> 배달 완료 처럼 단순히 값 변경을 넘어 프로세스의 상태가 변경되는 경우
  - POST의 결과로 새로운 리소스가 생성되지 않을 수도 있음.
  - ex) POST /orders/{orderId}/start-delivery (URI에 동사명을 사용한 것을 **컨트롤 URI**라고 함)
- **3. 다른 메서드로 처리하기 애매한 경우
  - ex) JSON으로 조회 데이터를 넘겨야 하는데, GET 메서드를 사용하기 어려운 경우(GET 에선 query string을 사용)
  - 애매하면 POST

### PUT
```
PUT /members/100 HTTP/1.1
Content-Type: appliation/json

{
  "username": "hello",
  "age": 20
}
```
- **리소스를 대체**
  - 리소스가 있으면 대체
  - 리소스가 없으면 생성
  - 쉽게 이야기해서 덮어버림(merge, paste)
- **중요! 클라이언트가 리소스를 식별**
  - 클라이언트가 리소스의 위치를 알고 URI 지정(/members/**100**)
  - POST와 차이점

#### 리소스가 있는 경우

- 클라이언트 요청 메세지(request)
```
PUT /members/100 HTTP/1.1
Content-Type: application/json

{
  "username": "old",
  "age": 50
}
```

- 서버에서 있는 기존 데이터
```
{
  "username": "young",
  "age": 20
}
```

- 데이터 대체
```
{
  "username": "old",
  "age": 50
}
```


#### 리소스가 없는 경우

- 클라이언트 요청 메세지(request)
```
PUT /members/100 HTTP/1.1
Content-Type: application/json

{
  "username": "old",
  "age": 50
}
```


- 신규 리소스 생성
```
{
  "username": "old",
  "age": 50
}
```

#### 주의! 리소스를 완전히 대체한다.

- 클라이언트 요청 메세지(request)
```
PUT /members/100 HTTP/1.1
Content-Type: application/json

{
  "age": 50
}
```

- 서버에서 있는 기존 데이터
```
{
  "username": "young",
  "age": 20
}
```

- 데이터 대체
```
{
  "age": 50
}
```

### PATCH
```
PATCH /members/100 HTTP/1.1
Content-Type: application/json

{
  "age": 50
}
```
- 리소스 부분 변경
- PATCH가 지원되지 않는 서버도 있음. 그런 경우 POST 사용

#### 리소스 부분 변경
```
PATCH /members/100 HTTP/1.1
Content-Type: application/json

{
  "age": 50
}
```

- 서버에서 있는 기존 데이터
```
{
  "username": "young",
  "age": 20
}
```

- 데이터 부분 변경
```
{
  "username": "young",
  "age": 50
}
```

### DELETE
```
DELETE /members/100 HTTP/1.1
Host: localhost:8080

```
- 리소스 제거


#### 리소스 제거
```
Delete /members/100 HTTP/1.1
Host: localhost:8080

```

- 서버에서 있는 기존 데이터
```
{
  "username": "young",
  "age": 20
}
```

- member 100 리소스 제거 리소스 제거

## HTTP 메서드의 속성
- 안전(Safe Methods)
- 멱등(Itempotent Methods)
- 캐시 가능(Cacheable Methods)

### 안전 
- 여러 번 호출해도 리소스를 변경하지 않는다.
- 여러 번 호출 시 로그가 쌓이는 것은 고려하지 않음. 안전에 대해선 해당 리소스 변경 여부만 고려함.

### 멱등
- *f(f(x)) = f(x)*
- 한 번 호출하든, 두 번 호출하든 100번 호출하든 해당 리소스의 결과가 같다.
- 멱등 메서드
  - **GET**: 해당 리소스의 항상 같은 결과가 조회된다.
  - **PUT**: 해당 리소스의 최종 대체된 결과는 같다.
  - **DELETE**: 해당 리소스의 삭제된 결과는 같다.

- **POST**: 멱등이 아니다. 두 번 호출하면 같은 결제가 중복해서 발생할 수 있다.
- **멱등은 재요청 중간에 리소스가 변경되는 것은 고려하지 않음.**
  - ex) GET 호출 > PUT 호출 > GET 호출

#### 멱등 활용
- 자동 복구 메커니즘
- 서버가 TIMEOUT 등으로 정상 응답을 못 주었을 때, 클라이언트가 같은 요청을 다시 해도 되는가에 대한 판단 근거

### 캐시 가능
- 응답 결과 리소스를 캐시해서 사용해도 되는가?
- GET, HEAD, POST, PATCH 캐시 가능
- 실제로는 GET, HEAD 정도만 캐시로 사용
  - POST, PATCH는 본문 내용까지 캐시 키로 고려해야하는데, 구현이 쉽지 않음.