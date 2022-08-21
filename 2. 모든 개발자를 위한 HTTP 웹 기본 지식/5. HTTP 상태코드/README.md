# HTTP 상태 코드

## 상태 코드

### 클라이언트가 보낸 요청의 처리 상태를 응답에서 알려주는 기능

- 1xx (Informational): 요청이 수신되어 처리 중(잘 사용되지 않음)
- 2xx (Successful): 요청 정상 처리
- 3xx (Redirection): 요청을 완료하려면 추가 행동이 필요
- 4xx (Client Error): 클라이언트 오류, 잘못된 문법 등으로 서버가 요청을 수행할 수 없음.
- 5xx (Server Error): 서버 오류, 서버가 정상 요청을 처리하지 못함.

## 만약 모르는 상태 코드가 나타나면?

- 클라이언트가 인식할 수 없는 상태코드를 서버가 반환하면?
- 클라이언트는 상위 상태 코드로 해석해서 처리
- 미래에 새로운 상태 코드가 추가되어도 클라이언트를 변경하지 않아도 됨.
- ex)
  - 299 ??? -> 2xx (Successful)
  - 451 ??? -> 4xx (Client Error)
  - 599 ??? -> 5xx (Server Error)

## 1xx (Informational)

### 요청이 수신되어 처리 중

- 거의 사용하지 않으므로 생략

## 2xx (Successful)

### 클라이언트의 요청을 성공적으로 처리

- 200 OK
- 201 Created (주로 POST 로 등록 시)
- 202 Accepted
- 204 No Content

## 200 OK
### 요청 성공


요청
```
GET /members/100 HTTP/1.1
Host: localhost:8080

```

응답
```
HTTP1.1 200 OK
Content-Type: application/json
Content-Length: 34

{
  "username": "young",
  "age": 20
}
```

## 201 Created
### 요청 성공해서 새로운 리소스가 생성됨


요청
```
POST /members HTTP/1.1
Content-Type: application/json

{
  "username": "young",
  "age": 20
} 
```

응답
```
HTTP1.1 201 Created
Content-Type: application/json
Content-Length: 34
Location: /members/100

{
  "username": "young",
  "age": 20
}
```

> 생성된 리소스는 응답의 **Location** 헤더 필드로 식별

## 202 Accepted

### 요청이 접수되었으나 처리가 완료되지 않았음.

- 배치처리 같은 곳에서 사용
- ex) 요청 접수 후 1시간 뒤에 배치 프로세스가 요청을 처리함

## 204 No Content
### 서버가 요청을 성공적으로 수행했지만, 응답 페이로드 분문에 보낼 데이터가 없음.

- ex) 웹 문서 편집기에서 save 버튼
- save 버튼의 결과로 아무 내용이 없어도 된다.
- save 버튼을 눌러도 같은 화면을 유지해야 한다.
- 결과 내용이 없어도 204 메세지(2xx)만으로 성공을 인식할 수 있다.

## 3xx (Redirection)

### 요청을 완료하기 위해 유저 에이전트(웹 브라우저 등)의 추가 조치 필요

- 300 Multiple Choices (잘 사용되지 않음.)
- 301 Moved Permanently
- 302 Found
- 303 See Other
- 304 Not Modified
- 307 Temporary Redirect
- 308 Permanent Redirect

## 리다이렉션 이해

- 웹 브라우저는 3xx 응답 결과에 Location 헤더가 있으면 Location 위치로 자동 이동(리다이렉트)

## 리다이렉션 이해

### 자동 리다이렉트 흐름

1. 요청
```
GET /event HTTP/1.1
Host: localhost:8080
```

2. 응답
```
HTTP/1.1 301 Moved Permanently
Location: /new-event
```

3. 3xx대 응답 시 Location헤더 필드로 자동 리다이렉트 (/new-event)

4. 요청
```
GET /new-event HTTP/1.1
Host: localhost:8080
```

5. 응답
```
HTTP/1.1 200 OK
...
```

## 리다이렉션 이해

### 종류

- **영구 리다이렉션** - 특정 리소스의 URI가 영구적으로 이동
  - ex) /members -> /users
  - ex) /event -> /new-event
- **일시 리다이렉션** - 일시적인 변경
  - 주문 완료 후 주문 내역 화면으로 이동
  - PRG: Post/Redirect/Get
- **특수 리다이렉션**
  - 결과대신 캐시를 사용
  - 캐시 기간 만료에 대한 새 캐시 요청 등

## 영구 리다이렉션

### 301, 308

- 리소스의 URI가 영구적으로 이동
- 원래의 URL를 사용 X, 검색 엔진 등에서도 변경 인지
- **301 MovedPermanently**
  - **리다이렉트시 요청 메서드가 GET으로 변하고, 본문이 제거될 수 있음**(MAY)
- **308 Permanent Redirect**
  - 301과 기능은 같음
  - **리다이렉트시 요청 메서드와 본문 유지(처음 POST를 보내면 리다이렉트도 POST로 요청)**


## 영구다이렉션 - 301


1. 요청
```
POST /event HTTP/1.1
Host: localhost:8080

name=hello&age=20
```

2. 응답
```
HTTP/1.1 301 Moved Permanently
Location: /new-event
```

3. GET으로 변경, HTTP 바디 제거 후 redirect

4. 요청
```
GET /new-event HTTP/1.1
Host: localhost:8080
```

5. 응답
```
HTTP/1.1 200 OK
...
```

## 영구다이렉션 - 308


1. 요청
```
POST /event HTTP/1.1
Host: localhost:8080

name=hello&age=20
```

2. 응답
```
HTTP/1.1 308 Permanent Redirect
Location: /new-event
```

3. POST, 메세지 본문 유지

4. 요청
```
POST /new-event HTTP/1.1
Host: localhost:8080

name=hello&age=20
```

5. 응답
```
HTTP/1.1 200 OK
...
```

> 이런식으로 잘 사용되지 않음

