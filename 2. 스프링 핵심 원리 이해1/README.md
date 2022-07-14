
### 프로젝트 생성
- 사전 준비물
  - Java 11
  - IDE: IntteliJ 또는 Eclipse 설치

**[스프링 부트 스타터](https://start.spring.io) 사이트로 이동해서 스프링 프로젝트 생성**

- 프로젝트 선택
  - Project: Gradle Project
  - Spring Boot: 2.6.x (강의 영상에선 2.3.x 선택함. SNAPSHOT, M2 등은 정식 릴리즈 버전이 아님)
  - Language: Java
  - Packaging: Jar
  - Java: 11
- Project Metedata
  - groupid: com.waw(강의 영상에선 hello 입력)
  - artifactId: core
  - Dependencies: 선택하지 않는다.(아무 것도 선택하지 않아도 스프링 코어쪽 라이브러리만 가져온다.)

### Gradle 전체 설정
```groovy
plugins {
	id 'org.springframework.boot' version '2.6.9'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'com.waw'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

아래 사진처럼 CoreApplication을 실행해보자.

![CoreApplicationRun](./image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-07-15%20%EC%98%A4%EC%A0%84%207.22.01.png)

![CoreApplicationRunResult](./image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-07-15%20%EC%98%A4%EC%A0%84%207.28.28.png)

실행 후 끝나면 정상작동한 거다.





## 비즈니스 요구사항과 설계
- 회원
  - 회원을 가입하고 조회할 수 있다.
  - 회원은 일반과 VIP 두 가지 등급이 있다.
  - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다(미확정)

- 주문과 할인 정책
  - 회원은 상품을 주문할 수 있다.
  - 회원 등급에 따라 할인 정책을 적용할 수 있다.
  - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라.(나중에 변경될 수 있다.)
  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다.(미확정)

요구사항을 보면 회원 데이터, 할인 정책 같은 부분은 지금 결정하기 어려운 부분이다. 그렇다고 이런 정책이 결정될 때까지 개발을 무기한 기다릴 수도 없다. 우리는 앞에서 배운 객체 지향 설계 방법이 있지 않은가!

인터페이스를 만들고 구현체를 언제든지 갈이끼울 수 있도록 설계하면 된다. 그럼 시작해보자

> **참고**: 프로젝트 환경설정을 편리하게 하려고 스프링 부트를 사용한 것이다. 지금은 스프링 없는 순수한 자바로만 개발을 진행한다는 점을 꼭 기억하자! 스프링 관련은 한참 뒤에 등장한다.

## 회원 도메인 설계
- 회원 도메인 요구사항
  - 회원을 가입하고 조회할 수 있다.
  - 회원은 일반과 VIP 두 가지 등급이 있다.
  - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다(미확정)

**회원 도메인 협력관계**

![회원 도메인 협력관계](./image/%ED%9A%8C%EC%9B%90%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%ED%98%91%EB%A0%A5%EA%B4%80%EA%B3%84.png)

- 클라이언트는 회원가입, 회원 조회를 하기 위해 회원서비스를 호출한다.
- 회원 저장소는 정해지지 않았기 때문에 인터페이스로 생성한다.
- 회원 저장소는 메모리, DB, 외부 시스템으로 구현한다.

**회원 클래스 다이어그램**
![회원 클래스 다이어그램](./image/%ED%9A%8C%EC%9B%90%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)
