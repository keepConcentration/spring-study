## 인터넷 네트워크

### 인터넷 통신

클라이언트와 서버가 케이블로 연결돼있으면 연결한 케이블을 통해 메세지를 주고받을 수 있다.

클라이언트와 서버의 물리적인 위치가 너무 멀고 서로 인터넷으로 연결돼있다면?

해저 광케이블, 인공위성과 같은 수많은 중간 노드룰 통해 메세지가 어떻게 안전하게 도착할까?
어떤 규칙으로 어떻게 수많은 복잡한 상황을 헤쳐서 메세지가 안전하게 도착할까?

이를 이해하려면 IP에 대해서 학습할 필요가 있다.

### IP(인터넷 프로토콜)

해외로 "Hello, world!" 라는 메세지를 복잡한 인터넷 망을 통해 보내려한다. 이를 수행하려면 최소한의 규칙이 있어야한다.

IP 주소를 통해서 메세지를 전달할 수 있다.

클라이언트는 IP 주소를 부여받아야한다.(100.100.100.1)
서버도 IP 주소를 부여받아야한다.(200.200.200.2)

### IP의 역할

- 지정한 IP 주소에 데이터 전달
- 패킷 이라는 통신 단위로 메세지 전달

### IP 패킷 정보

- 출발지 IP
- 목적지 IP
- 메세지

### 클라이언트 패킷 전달
  - 출발: 100.100.100.1
  - 도착: 200.200.200.2
  - Hello, world!

### 서버 패킷 전달
  - 출발: 200.200.200.2
  - 도착: 100.100.100.1
  - OK

### IP 프로토콜의 한계

- **비연결성**
  - 패킷을 받을 대상이 없거나 서비스 불능 상태여도 패킷 전송 (서버가 꺼졌어도 메세지를 전송(편지 보내는 느낌)

- **비신뢰성**
  - 중간에 패킷이 사라지면?
  - 패킷이 순서대로 안오면? (1. Hello 2. world! 전송 > 2. world!, 1. Hello 순서대로 도착할 수도 있음.)

- **프로그램 구분**
  - 같은 IP를 사용하는 서버에서 통신하는 애플리케이션이 둘 이상이면? (인터넷 게임, 음악을 들을 때도 같은 IP를 클라이언트로 사용함.)

> 이 IP만으로는 위 문제를 해결할 수 없다. 이 문제를 해결할 수 있는게 TCP이다.


### TCP

#### 인터넷 프로토콜 스택의 4계층

- 애플리케이션 계층(HTTP, FTP)
  + 웹브라우저, 게임 등

- 전송 계층(TCP, UDP)
  + OS
  
- 인터넷 계층(IP)
  
- 네트워크 인터페이스 계층
  + LAN 카드

애플리케이션(채팅 프로그램)으로 해외로 Hello 메세지를 전송할 때

소켓 라이브러리를 통해 OS 계층에 Hello 메세지를 전달

OS계층에서 메세지에 TCP 정보를 생성

생성된 TCP 정보에 IP 패킷을 생성(출발지, 목적지, 메세지, TCP 정보) -> TCP/IP

LAN 카드, LAN 드라이버를 통해 Ethernet frame을 포함해서 인터넷으로 전송(MAC 주소같은 물리적은 정보 등)

> 패킷 : 수화물(Package) + 덩어리(Bucket) 합성어

### TCP/IP 패킷 정보

- IP 패킷
- TCP 세그먼트
  - 출발지 PORT
  - 목적지 PORT
  - 전송 제어
  - 순서
  - 검증 정보
  - 전송 데이터

### TCP 특징

전송 제어 프로토콜(Transmission Controle Protocol)

- 연결 지향 - TCP 3 way handshake (가상연결) - 연결 후 메세지 전송
- 데이터 전달 보증 - 패킷 누락 시 메세지 전송 실패 여부 알 수 있음
- 순서 보장
- 신뢰할 수 있는 프로토콜
- 현재는 대부분 TCP 사용

### TCP 3 way handshake

1. 클라이언트에서 서버로 SYN(Synchronize) 메세지 전송
2. 서버는 클라이언트로 SYN+ACK 전송
3. 클라이언트는 서버로 ACK 전송
4. 데이터 전송

**SYN(Synchronize)** : 접속 요청

**ACK**: 요청 수락

> 참고: 3. **ACK**와 함께 데이터 전송 가능

SYN, ACK 응답 여부에 따라 데이터 전송 여부가 정해짐.

위 3 way handshake는 논리적 연결임.(실제 연결 X)


### 데이터 전달 보증

데이터 전송 시 서버에서 데이터 잘 받았다는 응답을 보냄.

### 순서 보장

패킷 1, 2, 3 순서대로 보냈을 때 서버에서 패킷 1, 3, 2 순서로 도착했을 때 서버에선 패킷 2번부터 다시 보내달라고 요청함.

### UDP 특징

사용자 데이터그램 프로토콜(User Datagram Protocol)

- 기능이 거의 없음(하얀 도화지에 비유)

- 연결지향 - TCP 3way handshake X

- 데이터 전달 보증 X

- 순서 보장 X

- 데이터 전달 및 순서가 보장되지 않지만, 단순하고 빠름

- 정리
  - IP와 거의 같다. +PORT +체크섬(검증) 정도만 추가
  - 애플리케이션에서 추가 작업 필요

HTTP3 스펙에서 UDP 프로토콜을 사용함.


## PORT

### 같은 IP 내에서 프로세스 구분

- 한 번에 둘 이상 연결해야하면?

- 게임, 화상 통화, 웹 브라우저 요청 세 가지를 한꺼번에 통신할 때 응답 패킷이 어느 서버에서 응답하는 패킷인 지 알 수 없음.

- TCP 세그먼트에 **출발지 PORT**, **목적지 PORT** 정보가 포함되어있다.

- 같은 IP 내에서 프로세스를 구분하는 역할을 한다.

### PORT

- 0 ~ 65535 할당 가능

- 0 ~ 1023: 잘 알려진 포트, 사용하지 않는 것이 좋음.

- FTP: 20, 21

- TELNET: 23

- HTTP: 80

- HTTPS: 443


## DNS

- IP는 기억하기 어렵다.

- IP는 변경될 수 있다.

### DNS(Domain Name System)

- 전화번호부

- 도메인 명을 IP주소로 반환

### DNS 사용

1. 클라이언트가 **DNS 서버**로 google.com 요청

2. DNS 서버는 google.com의 IP를 응답

3. 응답받은 IP로 접근

## URI

### URI와 웹 브라우저 요청 흐름

- URI
- 웹 브라우저 요청 흐름


### URI(Uniform Resource Identifier)

- 리소스를 식별하는 통합된 방법
- URI는 로케이터(**L**ocator), 이름(**N**ame) 또는 둘 다 추가로 분류될 수 있다.

### URI? URL? URN?

URL(Uniform Resource Locator)

- ex) foo://example.com:8042/over/there?name=ferret#nose

URN(Uniform Resource Name)
- ex) urn:example:animal:ferret:nose

> `foo`: scheme

> `example.com:8042`: authority

> `/over/there`, `example:animal:ferret:nose`: path

> `name=ferret`: query

> `nose`: fragment

### URI 뜻

- **U**niform: 리소스 식별하는 통일된 방식
- **R**esource: 자원, URI로 식별할 수 있는 모든 것(제한 없음)
  - ex) html파일, 실시간 교통정보 등
- **I**dentifier: 다른 항목과 구분하는데 필요한 정보

### URL, URN 단어 뜻

- URL - Locator: 리소스가 있는 위치를 지정
- URN - Name: 리소스에 이름을 부여
- 위치는 변할 수 있지만, 이름은 변하지 않는다.
- urn:isbn:8960777331 (어떤 책의 isbn URN)
- URN 이름만으로 실제 리소스를 찾을 수 있는 방법이 보편화 되지 않음
- 앞으로 URI를 URL과 같은 의미로 이야기하겠음.


### URL 전체 문법

scheme://[userinfo@]host[:port][/path][?query][#fragment]

https://www.google.com:443/search?q=hello&hl=ko

- 프로토콜(https)
- 호스트명(www.google.com)
- 포트번호(443)
- 패스(/search)
- 쿼리 파라미터(q=hello&hl=ko)

### URL scheme

**scheme**://[userinfo@]host[:port][/path][?query][#fragment]

**https**://www.google.com:443/search?q=hello&hl=ko

- 주로 프로토콜(https) 사용
- 프로토콜: 어떤 방식으로 자원에 접근할 것인가 하는 약속 규칙
  - ex) http, https, ftp 등
- http는 80포트, https는 443포트를 주로 사용, 포트는 생략 가능
- https는 http에 보안 추가(HTTP Secure)

### URL userinfo

scheme://**[userinfo@]** host[:port][/path][?query][#fragment]

https://www.google.com:443/search?q=hello&hl=ko

- URL에 사용자정보롤 포함해서 인증
- 거의 사용하지 않음.

### URL host

scheme://[userinfo@]**host**[:port][/path][?query][#fragment]

https://**www.google.com**:443/search?q=hello&hl=ko

- 호스트명
- 도메인명 또는 IP주소를 직접 사용 가능

### URL port

scheme://[userinfo@]host**₩[:port]**[/path][?query][#fragment]

https://www.google.com **:443**/search?q=hello&hl=ko

- 포트(PORT)
- 접속 포트
- 일반적으로 생략, 생략 시 http는 80, https는 443

### URL port

scheme://[userinfo@]host[:port]**[/path]**[?query][#fragment]

https://www.google.com:443/**search**?q=hello&hl=ko

- 리소스 경로(path), 계층적 구조
- ex)
  - /home/file1.jpg
  - /members
  - members/100, items/iphone12

### URL query

scheme://[userinfo@]host[:port][/path]**[?query]**[#fragment]

https://www.google.com:443/search **?q=hello&hl=ko**

- key=value 형태
- ?로 시작, &로 추가 가능 ?keyA=valueA&keyB=valueB
- query parameter, query string 등으로 불림
- 웹 서버에 제공하는 파라미터, 문자 형태

### URL query

scheme://[userinfo@]host[:port][/path][?query]**[#fragment]**

https://dos.spring.io/spring-boot/docs/current/reference/html/getting-started.html **#getting-started-introducing-spring-boot**

- html 내부 북마크 등에 사용
- 서버에 전송하는 정보 아님